Kevin Kredit

CIS 656

Lab01

2018/09/10

## Subject
This report describes the behavior of multi-threaded applications when
accessing
shared data with and without synchronization techniques. Program A and B both
access a shared Counter object and increment the `counter` value 1000000 times
per thread, where the number of threads is specified by a command-line
argument. Program A does not use synchronization, but Program B does.

## Data
The following table shows the final counter values of programs A (no
synchronization) and B (syncronization) for 2-32 threads:

| Program |      2 |      4 |      8 |      16 |      32 |
|---------|--------|--------|--------|---------|---------|
| A       | 1944505| 3373693| 7412941| 12590144| 20444447|
| B       | 2000000| 4000000| 8000000| 16000000| 32000000|

The following table shows the runtimes in milliseconds of programs A (no
synchronization) and B (syncronization) for 2-32 threads:

| Program |  2 |  4 |  8 | 16 |  32 |
|---------|----|----|----|----|-----|
| A       |  16|  15|  26|  22|   32|
| B       | 104| 226| 448| 886| 1789|


## Questions
 1. Is the counter always equal to n * 1000000, where n is the number of threads
created?  Explain why, or why not. (Program A)

For program A, the counter is _not_ always n * 1000000. There is a race
condition around the reading and writing of the shared Counter's `counter`
variable. Without synchronization control, different threads might perform
reads at the same time. When they get to the increment stage, the second 
thread to get there will be working with stale data--the first
thread has already updated the counter, but the second thread doesn't know
it. The end result is an indeterminate value, but is always less than n *
1000000.

 2. Is the counter always equal to n * 1000000, where n is the number of threads
created?  Explain why, or why not. (Program B)

For program B, the counter _is_ always n * 1000000. Because of the
`synchronized` property of `safeIncrement`, a single thread performs a read
and write atomically, so no thread is ever operating with stale data.

 3. Analyze the differences in elapsed time between Program A and Program B.
Is there a significant difference?  Explain why or why not.

There is a significant difference in the runtimes of programs A and B.
Synchronization between threads requires overhead in two forms: additional
synchronization code has to be run, and only one thread is actually doing work
at any give time. The second reason is not true in all mutlithreaded
applications, but in this one where the entire workload is in the critical
section, it plays a big role.
