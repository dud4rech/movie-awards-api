CREATE TABLE MOVIES (year INT, title VARCHAR(100), studios VARCHAR(100), producers VARCHAR(255), winner boolean)
AS SELECT * FROM CSVREAD('classpath:/movielist.csv', null, 'fieldSeparator=;');
CREATE ALIAS STRING_REPLACE AS '
String stringReplace(String value, String regex, String replace) {
    return value.replaceAll(regex, replace);
}
';
CREATE ALIAS STRING_TRIM AS '
String stringTrim(String value) {
    return value.trim();
}
';
CREATE VIEW WINNERS AS
SELECT M.year, STRING_TRIM(REGEXP_SUBSTR(STRING_REPLACE(M.producers, ' and ', ','), '[^,]*', 1, nums.n)) AS NAME
FROM MOVIES M
JOIN (SELECT * FROM SYSTEM_RANGE(1, 20)) AS nums(n)
ON REGEXP_SUBSTR(STRING_REPLACE(M.producers, ' and ', ','), '[^,]*', 1, nums.n) IS NOT NULL
AND REGEXP_SUBSTR(STRING_REPLACE(M.producers, ' and ', ','), '[^,]*', 1, nums.n) <> ''
WHERE M.winner IS TRUE;
