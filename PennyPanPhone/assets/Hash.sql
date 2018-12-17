DECLARE @pswd NVARCHAR(MAX) = 'APassword'; 
DECLARE @salt VARBINARY(4) = CRYPT_GEN_RANDOM(4);
DECLARE @hash VARBINARY(MAX); 
SET @hash = 0x0200 + @salt + HASHBYTES('SHA2_512', CAST(@pswd AS VARBINARY(MAX)) + @salt);

SELECT @hash AS HashValue, PWDCOMPARE(@pswd,@hash) AS IsPasswordHash;