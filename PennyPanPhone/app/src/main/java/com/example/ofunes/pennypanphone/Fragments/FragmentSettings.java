package com.example.ofunes.pennypanphone.Fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v14.preference.MultiSelectListPreference;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.SeekBarPreference;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ofunes.pennypanphone.BackgroundSoundService;
import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.FragmentOption;
import com.example.ofunes.pennypanphone.R;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSettings extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    LoggedinViewModel viewModel;
    SwitchPreferenceCompat musicToggle;
    SeekBarPreference musicVolume;
    Preference signoff, bugReport;
    MultiSelectListPreference hireWorkers, fireWorkers;
    PreferenceCategory admin;
    PreferenceScreen preferenceScreen;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.settings, rootKey);
        viewModel = ViewModelProviders.of(getActivity()).get(LoggedinViewModel.class);
        musicToggle = (SwitchPreferenceCompat)findPreference("musicToggle"); musicToggle.setOnPreferenceChangeListener(this);
        musicVolume = (SeekBarPreference)findPreference("musicVolume"); musicVolume.setOnPreferenceChangeListener(this);
        signoff = findPreference("signoff"); signoff.setOnPreferenceClickListener(this);
        bugReport = findPreference("bugReport"); bugReport.setOnPreferenceClickListener(this);
        hireWorkers = (MultiSelectListPreference)findPreference("hireWorkers");
        fireWorkers = (MultiSelectListPreference)findPreference("fireWorkers");
        admin = (PreferenceCategory)findPreference("adminCategory");
        preferenceScreen = (PreferenceScreen)findPreference("preferenceScreen");

        if(musicToggle.isChecked())
            musicVolume.setEnabled(true);
        else
            musicVolume.setEnabled(false);

        if(!viewModel.getCliente().isPanadero())
        {
            preferenceScreen.removePreference(hireWorkers);
            preferenceScreen.removePreference(fireWorkers);
            preferenceScreen.removePreference(admin);
        }
        else
        {
            ArrayList<String[]> listado = obtenerUsuarios();
            if(listado.get(0).length == 0)
                hireWorkers.setEnabled(false);
            else
            {
                hireWorkers.setEntries(listado.get(0));
                hireWorkers.setEntryValues(listado.get(0));
            }

            if(listado.get(1).length == 0)
                fireWorkers.setEnabled(false);
            else
            {
                fireWorkers.setEntries(listado.get(1));
                fireWorkers.setEntryValues(listado.get(1));
            }


        }
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String key = preference.getKey();

        switch(key)
        {
            case "musicToggle":
                boolean isMusicOn = (Boolean)newValue;
                if(isMusicOn)
                    getActivity().startService(new Intent(getActivity(), BackgroundSoundService.class));
                else
                    getActivity().stopService(new Intent(getActivity(), BackgroundSoundService.class));

                musicVolume.setEnabled(isMusicOn);
                saveMusicState(isMusicOn);
                break;

            case "musicVolume":
                Toast.makeText(getActivity(), getString(R.string.noWay), Toast.LENGTH_SHORT).show();
                break;
        }



        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        String key = preference.getKey();

        switch(key)
        {
            case "bugReport":
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "carterasindinero@gmail.com", null));
                emailIntent
                        .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.bugReportEmailSubject))
                        .putExtra(Intent.EXTRA_TEXT, getString(R.string.bugReportEmailText));
                startActivity(emailIntent);
                break;

            case "signoff":
                new MaterialStyledDialog.Builder(getContext())
                        .setTitle(R.string.signoffDialogTitle)
                        .setDescription(R.string.signoffDialogContent)
                        .setPositiveText(R.string.signoffDialogAffirmative)
                        .setNegativeText(R.string.signoffDialogNegative)
                        .setStyle(Style.HEADER_WITH_ICON)
                        .setIcon(R.drawable.icon_exit)
                        .setHeaderColor(R.color.ErrorRed)
                        .setCancelable(true)
                        .withIconAnimation(true)
                        .withDialogAnimation(true, Duration.FAST)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                viewModel.getFragmentOption().setValue(FragmentOption.SIGNOFF);
                            }
                        })
                        .show();
                break;
        }

        return false;
    }

    private void saveMusicState(boolean isMusicOn)
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.sharedPreferencesName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.sharedPreferencesMusic), isMusicOn);
        editor.apply();
    }

    private ArrayList<String[]> obtenerUsuarios()
    {
        ArrayList<String> workers = new ArrayList<>();
        ArrayList<String> clients = new ArrayList<>();
        ArrayList<String[]> complete = new ArrayList<>();

        for(Cliente cliente : viewModel.getListadoClientes().getValue())
        {
            if(cliente.isPanadero())
                workers.add(cliente.getUsername());
            else
                clients.add(cliente.getUsername());
        }

        String[] workersArray = new String[workers.size()];
        String[] clientsArray = new String[clients.size()];

        workers.toArray(workersArray);
        clients.toArray(clientsArray);

        complete.add(clientsArray);
        complete.add(workersArray);

        return complete;
    }
}
