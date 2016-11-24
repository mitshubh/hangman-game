
/**
 * Created by mitshubh on 11/6/16.
 */
======================================================
HANGMAN ---- HULU ----- PROGRAMMING ---- CHALLENGE
======================================================

README.txt ---- 

 1) The entry point of the system is HangmanEntry.java which loops on playing new games till the user exits.
 2) Entire codebase for hangman is in java
 3) For improving accuracy, SCOWL dataset containing variants of english words of different sizes is first ingested into the system and the new character is based on the max likelihood calculated based on the length, known nearby characters and the possible words existing in the SCOWL directory.
 4) Instuctions to run -----
 	a) Navigate to the root directory i.e. in Hangman-Hulu
 	b) Run the following command 
 		java -jar out/artifacts/Hangman_jar/Hangman.jar
 5) Average win rate ~= 10% for more than 150 iterations (Could be improved by using a better dictionary than SCOWL)
 6) Possible BUGS in the API ?? ----- There seems to be a (possible) bug in your API for single worded questions eg. _____ (say HEAD). 

Below is a sample execution run for such a word. If you see, the status returned when the word is successfully guessed (MORROW) is "ALIVE" and not "FREE" which is an anomaly when compared to other executions (where, when the word is correctly guessed, the status changes to "FREE")

------Single lettered word-----
______
e is false, 3 more tries.
______
a is false, 3 more tries.
______
s is false, 3 more tries.
______
r is true New status: ALIVE
__RR__
o is true New status: ALIVE
_ORRO_
w is true New status: ALIVE
_ORROW
b is false, 2 more tries.
_ORROW
m is true New status: ALIVE			////STATUS SHOULD HAVE BEEN FREE
MORROW


7) I noticed a similar issue for 2 letter words (but only once) ----- sample execution run

------2 lettered word-----
_______ ________
e is true New status: ALIVE
E___E__ ________
s is true New status: ALIVE
E_S_E__ ____S___
n is true New status: ALIVE
E_S_E_N ____S__N
i is true New status: ALIVE
E_S_E_N _I_ISI_N
a is true New status: ALIVE
EAS_E_N _I_ISI_N
r is true New status: ALIVE
EAS_ERN _I_ISI_N
t is true New status: ALIVE
EASTERN _I_ISI_N
d is true New status: ALIVE
EASTERN DI_ISI_N
o is true New status: ALIVE
EASTERN DI_ISION
v is true New status: ALIVE			///STATUS SHOULD HAVE BEEN FREE
EASTERN DIVISION



==========================================================
Submitted By : Shubham Mittal, CS Graduate Student, UCLA
==========================================================


