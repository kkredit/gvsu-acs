
-- File: companyDML-b-solution 
-- SQL/DML HOMEWORK (on the COMPANY database)
/*
Every query is worth 2 point. There is no partial credit for a
partially working query - think of this hwk as a large program and each query is a small part of the program.
--
IMPORTANT SPECIFICATIONS
--
(A)
-- Download the script file company.sql and use it to create your COMPANY database.
-- Dowlnoad the file companyDBinstance.pdf; it is provided for your convenience when checking the results of your queries.
(B)
Implement the queries below by ***editing this file*** to include
your name and your SQL code in the indicated places.   
--
(C)
IMPORTANT:
-- Don't use views
-- Don't use inline queries in the FROM clause - see our class notes.
--
(D)
After you have written the SQL code in the appropriate places:
** Run this file (from the command line in sqlplus).
** Print the resulting spooled file (companyDML-b.out) and submit the printout in class on the due date.
--
**** Note: you can use Apex to develop the individual queries. However, you ***MUST*** run this file from the command line as just explained above and then submit a printout of the spooled file. Submitting a printout of the webpage resulting from Apex will *NOT* be accepted.
--
*/
-- Please don't remove the SET ECHO command below.
SPOOL companyDML-b.out
SET ECHO ON
-- ------------------------------------------------------------
-- 
-- Name: Kevin Kredit
--
-- -------------------------------------------------------------
--
-- NULL AND SUBSTRINGS -------------------------------
--
/*(10B)
Find the ssn and last name of every employee whose ssn contains two consecutive 8's, and has a supervisor. Sort the results by ssn.
*/
SELECT ssn, lname
FROM employee e
WHERE ssn LIKE '%88%' AND super_ssn IS NOT NULL
ORDER BY ssn;
--
-- JOINING 3 TABLES ------------------------------
-- 
/*(11B)
For every employee who works for more than 20 hours on any project that is controlled by the research department: Find the ssn, project number,  and number of hours. Sort the results by ssn.
*/
SELECT W.essn, P.pnumber, W.hours
FROM project P, department D, works_on W
WHERE P.dnum = D.dnumber AND
      D.dname = 'Research' AND
      W.pno = P.pnumber AND
      W.hours > 20
ORDER BY W.essn;
--
-- JOINING 3 TABLES ---------------------------
--
/*(12B)
Write a query that consists of one block only.
For every employee who works less than 10 hours on any project that is controlled by the department he works for: Find the employee's lname, his department number, project number, the number of the department controlling it, and the number of hours he works on that project. Sort the results by lname.
*/
SELECT E.lname, E.dno, P.pnumber, P.dnum, W.hours
FROM employee E, project P, works_on W
WHERE E.dno = P.dnum AND
      W.essn = E.ssn AND
      W.pno = P.pnumber AND
      W.hours < 10
ORDER BY E.lname;
--
-- JOINING 4 TABLES -------------------------
--
/*(13B)
For every employee who works on any project that is located in Houston: Find the employees ssn and lname, and the names of his/her dependent(s) and their relationship(s) to the employee. Notice that there will be one row per qualyfing dependent. Sort the results by employee lname.
*/
SELECT DISTINCT E.ssn, E.lname, DP.dependent_name, DP.relationship
FROM employee E, dependent DP, project P, works_on W
WHERE E.ssn = W.essn AND
      W.pno = P.pnumber AND
      P.plocation = 'Houston' AND
      DP.essn = E.ssn
ORDER BY E.lname;
--
-- SELF JOIN -------------------------------------------
-- 
/*(14B)
Write a query that consists of one block only.
For every employee who works for a department that is different from his supervisor's department: Find his ssn, lname, department number; and his supervisor's ssn, lname, and department number. Sort the results by ssn.  
*/
SELECT E.ssn, E.lname, E.dno, S.ssn AS super_ssn, S.lname, S.dno
FROM employee E, employee S
WHERE E.super_ssn = S.ssn AND
      E.dno != S.dno
ORDER BY E.ssn;
--
-- USING MORE THAN ONE RANGE VARIABLE ON ONE TABLE -------------------
--
/*(15B)
Find pairs of employee lname's such that the two employees in the pair work on the same project for the same number of hours. List every pair once only. Sort the result by the lname in the left column in the result. 
*/
SELECT E1.lname, E2.lname
FROM employee E1, employee E2, works_on W1, works_on W2
WHERE E1.ssn = W1.essn AND
      E2.ssn = W2.essn AND
      W1.pno = W2.pno AND
      W1.hours = W2.hours AND
      E1.lname < E2.lname
ORDER BY E1.lname;
--
/*(16B)
For every employee who has more than one dependent: Find the ssn, lname, and number of dependents. Sort the result by lname
*/
SELECT E.ssn, E.lname, COUNT(*) AS num_deps
FROM employee E, dependent DP
WHERE E.ssn = DP.essn
GROUP BY E.ssn, E.lname
HAVING COUNT(*) > 1
ORDER BY E.lname;
-- 
/*(17B)
For every project that has more than 2 employees working on and the total hours worked on it is less than 40: Find the project number, project name, number of employees working on it, and the total number of hours worked by all employees on that project. Sort the results by project number.
*/
SELECT P.pnumber, P.pname, COUNT(*) AS num_empl, SUM(W.hours) AS total_hrs
FROM project P, works_on W
WHERE P.pnumber = W.pno
GROUP BY P.pnumber, P.pname
HAVING COUNT(*) > 1 AND
       SUM(W.hours) < 40
ORDER BY P.pnumber;
--
-- CORRELATED SUBQUERY --------------------------------
--
/*(18B)
For every employee whose salary is above the average salary in his department: Find the dno, ssn, lname, and salary. Sort the results by department number.
*/
SELECT E.dno, E.ssn, E.lname, E.salary
FROM employee E, department D
WHERE E.dno = D.dnumber AND
      E.salary > (SELECT AVG(E2.salary)
                  FROM employee E2
                  WHERE E2.dno = D.dnumber
                  GROUP BY D.dnumber)
ORDER BY D.dnumber;
--
-- CORRELATED SUBQUERY -------------------------------
--
/*(19B)
For every employee who works for the research department but does not work on any one project for more than 20 hours: Find the ssn and lname. Sort the results by lname
*/
-- NOTE: Assuming that "works for the research department" means "works on any project belonging
--       to the research department", NOT "belongs to the research department themselves"
SELECT E.ssn, E.lname
FROM employee E, works_on W
WHERE E.ssn = W.essn AND
      EXISTS( SELECT P.dnum
              FROM department D, project P, works_on W2
              WHERE E.ssn = W2.essn AND
                    P.pnumber = W2.pno AND
                    P.dnum = D.dnumber AND
                    D.dname = 'Research' )
GROUP BY E.ssn, E.lname
HAVING 20 >= MAX(W.hours)
ORDER BY E.lname;
-- NOTE: Assuming that "works for the research department" means "belongs to the research
--       department themselves"
SELECT E.ssn, E.lname
FROM employee E, works_on W, department D
WHERE E.ssn = W.essn AND
      E.dno = D.dnumber AND
      D.dname = 'Research'
GROUP BY E.ssn, E.lname
HAVING 20 >= MAX(W.hours)
ORDER BY E.lname;
--
-- DIVISION ---------------------------------------------
--
/*(20B) Hint: This is a DIVISION query
For every employee who works on every project that is controlled by department 4: Find the ssn and lname. Sort the results by lname
*/
SELECT E.ssn, E.lname
FROM employee E
WHERE NOT EXISTS( (SELECT P1.pnumber
                   FROM project P1
                   WHERE P1.dnum = 4)
                  MINUS
                  (SELECT P2.pnumber
                   FROM project P2, works_on W
                   WHERE P2.dnum = 4 AND
                         E.ssn = W.essn AND
                         W.pno = P2.pnumber) )
ORDER BY E.lname;
--
SET ECHO OFF
SPOOL OFF

