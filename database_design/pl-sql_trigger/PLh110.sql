/*
This trigger implements part of the inclusion dependency bIC5 (see our
Sailors schema).It verifies, upon DELETE, or UPDATE on the Sailors table,
that boats.logKeeper is in Sailors.trainee
*/
-- Notice the use of PRAGMA AUTONOMOUS_TRANSACTION
-- -------------------------------------------------------
-- File: PLh110.sql
-- Author: Kevin Kredit
--
CREATE OR REPLACE TRIGGER bIC5_TB
BEFORE DELETE OR UPDATE OF trainee ON Sailors
FOR EACH ROW
DECLARE
   PRAGMA AUTONOMOUS_TRANSACTION; -- You’ll need this directive
   numRemTraineeEntries INTEGER;
   numBoatsLogkeeper INTEGER;
BEGIN
   SELECT COUNT(*) INTO numRemTraineeEntries
   FROM   Sailors S
   WHERE  (S.sid != :OLD.sid AND S.trainee = :OLD.trainee) OR
          (S.sid = :NEW.sid AND S.trainee = :NEW.trainee);
--
   SELECT COUNT (*) INTO numBoatsLogkeeper
   FROM   Boats B
   WHERE  B.logkeeper = :OLD.trainee;
--
   IF (numRemTraineeEntries = 0 AND numBoatsLogkeeper > 0)
   THEN
     RAISE_APPLICATION_ERROR(-20001, '+++++INSERT or UPDATE rejected. '||
                    'trainee..'||:OLD.trainee||'..is a LogKeeper');
   END IF;
END;
/
SHOW ERROR
-- -------------------------------------------------------
