@drop
@_create
@PLh110.sql
SPOOL PLh110.out
SET ECHO ON
--
-- Kevin Kredit
--
-- Testing the trigger for UPDATE
--
-- (1)
UPDATE Sailors SET trainee=95 where sid=85;
commit;
-- (2)
UPDATE Sailors SET trainee=71 where sid=64;
commit;
-- (3)
UPDATE Sailors SET trainee=31 where sid=58;
commit;
--
-- Testing the trigger for DELETE
--
-- (4)
DELETE FROM Sailors where sid=74;
commit;
-- (5)
DELETE FROM Sailors where sid=22;
commit;
--
-- (6)
-- Inspect the database
SELECT * FROM Sailors order by sid;
-- Drop the trigger
Drop Trigger bIC5_TB;
SET ECHO OFF
SPOOL OFF
