-- File: PLh10.sql
-- Author: Kevin Kredit
-- ----------------------------------
SET SERVEROUTPUT ON
SET VERIFY OFF
-- ----------------------------------
ACCEPT traineeID NUMBER PROMPT 'Enter a trainee ID: '
ACCEPT increment NUMBER PROMPT 'Enter an increment for his trainers: '
DECLARE
   sr     sailors%ROWTYPE;
   newRating sailors.rating%TYPE;
   CURSOR tCursor IS
      SELECT * FROM sailors S WHERE S.trainee = &traineeID;
BEGIN
   OPEN tCursor;
   LOOP
      -- Fetch the qualifying rows one by one
      FETCH tCursor INTO sr;
      EXIT WHEN tCursor%NOTFOUND;
      -- Print the sailor' old record
      DBMS_OUTPUT.PUT_LINE('+++   old row: '||sr.sid||chr(9)||sr.sname||chr(9)||sr.rating||
                           chr(9)||sr.age||chr(9)||sr.trainee);
      -- Increment the trainers' rating
      newRating := sr.rating + &increment;
      UPDATE sailors SET rating = newRating WHERE sailors.sid = sr.sid;
      -- Print the sailor' new record
      DBMS_OUTPUT.PUT_LINE('+++++ new row: '||sr.sid||chr(9)||sr.sname||chr(9)||newRating||
                           chr(9)||sr.age||chr(9)||sr.trainee);
   END LOOP;
   -- test whether the sailor has no trainers (Hint: test tCursor%ROWCOUNT)
   IF 0 = tCursor%ROWCOUNT THEN
      DBMS_OUTPUT.PUT_LINE('+++++ '||&traineeID||' is either not a sailor, or has no trainer');
   ELSE
      DBMS_OUTPUT.PUT_LINE('+++++ DB has been updated');
   END IF;
   CLOSE tCursor;
EXCEPTION
WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('+++++'||SQLCODE||'...'||SQLERRM);
END;
/
-- Let's see what happened to the database
SELECT *
FROM   sailors S
WHERE  S.trainee = '&traineeID';
UNDEFINE traineeID
UNDEFINE increment
