# Enigma machine simulator

## Features

- encode and decode Enigma messages with Enigma M3 and M4
- find machine setting for enciphered message using Hill-climbing algorithm 

## Enigma machine models that were in use during WWII

| # | Model                        | UKW    | Rotors | Choice of rotors                           |
| - | ---------------------------- | ------ | ------ | ------------------------------------------ |
| 1 | Wehrmacht (Heer & Luftwaffe) | B      | 3      | 5 normal                                   |
| 2 | Wehrmacht (Heer & Luftwaffe) | C      | 3      | 5 normal                                   |
| 3 | Kriegsmarine M3              | B      | 3      | 5 normal + 3 double notched                |
| 4 | Kriegsmarine M3              | C      | 3      | 5 normal + 3 double notched                |
| 5 | Kriegsmarine M4              | B thin | 4      | 5 normal + 3 double notched + Beta + Gamma |
| 6 | Kriegsmarine M4              | C thin | 4      | 5 normal + 3 double notched + Beta + Gamma |

Beta and Gamma could be placed aside the thin reflector only.

## WWII Ciphering procedure

Procedure:
- choose reflector and rotors
- setup rings on the rotors
- setup plugboard
- select a random basic position (Grundstellung) for example 'EHZ'
- select a random message key (Spruchschlussel) for example 'XWB'
- set rotors in basic position 'EHZ'
- encrypt message key 'XWB', for example got: 'TBS'
- set rotors in message key position 'XWB'
- encrypt message text, for example got: 'VJTLFJXWQFLT....'
- split encrypted message in groups of 4 or 5
- send message: 'EHZ TBS VJTLF JXWQF LT....'

## See also

- [Enigma M3](https://www.101computing.net/enigma/enigma-M3.html)
- [Virtual Enigma](https://enigma.virtualcolossus.co.uk/VirtualEnigma/index.htm)
- [How did the Enigma Machine work?](https://youtu.be/ybkkiGtJmkM)
- [How Enigma machine worked](https://hackaday.com/2017/08/22/the-enigma-enigma-how-the-enigma-machine-worked)
- [Rotors wiring](https://www.cryptomuseum.com/crypto/enigma/wiring.htm)

## Hill-Climbing algorithm

There is Hill-Climbing algorithm implementation in module [hill-climbing](hill-climbing).

This algorithm was described by Geoff Sullivan & Frode Weierud.

My implementation contains few improvements that help save time:
- reduce ring enumeration by varing enumeration step size
- use preliminary result filter

Algorithm basic steps:
- enumerate all reflectors and rotors 
- for each reflector and rotors set do
    - enumerate rings position of the right and middle rotors with step=2 (could be parametrised)
    - create thread
        - create Enigma instance
        - enumerate all rotor positions for all rotors
        - for each indicators set decipher message by Enigma instance and calculate fitness score as index of coincidence (IoC)
            - if this preliminary score is lower than some predefined threshold then skip this result
            - otherwise precompute all alphabet letters encipher for input text length steps - it's one more time saver for next steps
            - hill climb over plug pairs to find best plugboard setting

Precompute:
- set Enigma rotors position
- disable auto rotation
- repeat this procedure input text length times:
    - encipher all alphabet letters with current setting
    - increment rotor setting

We got two-dimensional array: int[][] where each line is an all alphabet letters encryption at the step of line number:
<pre>
[
      A    B    C    D
    ['X', 'Q', 'P', 'T', ...], // step 0
    ['V', 'H', 'A', 'N', ...], // step 1
    ... // text.length
]

'A' at step 0 is enciphered as 'X'
'B' at step 0 is enciphered as 'Q'
...
'A' at step 1 is enciphered as 'V'
'B' at step 1 is enciphered as 'H'
</pre>

Hill climb over plug pairs:
- repeat 10 times (or as many as plug pairs are used)
    - for each pair of left free letters repeat
        - put plug pair
        - decipher the message
        - calculate score using the proper fitness function according to logic
        - remove plug pair
    - choose plug pair with the highest score and put it

For long messages it's enough to use IoC for all plug pairs, but for short could be used next condition:
- from the 1st pair to the 4th - use Unigram fitness function
- from 5th to 9th - use Trigram
- for the last one - use Tetragram

At this project IoC fitness function has been simplified for time saving reason:
- original IoC: result = sum(histogram[i] * (histogram[i] - 1)) / (N * N - 1)
- implemented: sum(histogram[i] * histogram[i]) - just remove all static parts.

where:
- histogram is the number of each of the alphabet characters in the text
- N - text length

***Notice:** "X * X" is much faster than "Math.pow(X, 2)"*

### Other time saving optimisations:
- this project does not contain lambda functions in crucial parts because they are time-costly
- primitive array is much faster than List and Map 
- *char* type is smaller than *int* but all arithmetic operations auto converts *char* to *int*,
so all methods work with *int* letters representation only (index of letter = letter code minus code of 'A')
- rotors contain wiring of both directions: *front* and *back*;
*front* is predefined, *back* is precomputed at the runtime from the *front*
- *Streams* are too slow to use them in crucial sections, primitive *for* is much faster
- parallel *Streams* are good enough but controlled multithreading was decided to be better choice here
- special for search project was implemented "light" version of Enigma machine

## How to use Hill-Climbing search

You will need [HillClimbMain](hill-climbing/src/main/java/ua/in/asilichenko/enigma/hillclimbing/HillClimbMain.java).

### Stage 1 - obtain search parameters

Firstly - create sample text the same language as original message the same length.

Call the method *calculateThresholds* you will get two numbers: 
- test message score
- score of enciphered test message without plugboard

***Notice:** you may choose any rotor setting as you wish.*

Plain message score could be used to define early find candidate - just reduce this value for a one hundred or smth.

*If program find result that has score above candidate threshold - it immediately inform you and continues searching.*

Score of enciphered test message without plugboard could be used to cut off garbage (early known wrong results) - 
just reduce this value for a one hundred or smth.

*With 10 plug pairs it's to hard to determine real desired result but it's possible to cut off some wrong results.*

Don't be too greedy and don't setup thresholds too high.

For proper rotors and proper wiring positions the score is extremely high above average 
and this result keep holding for few adjacent **ring** positions, 
unfortunately this peek is extremely tight - just don't miss it.

### Stage 2 - test searching on single rotor set

Next - test you message with *searchForOneRotorSet* method, 
it could take from 1m to 4m on Intel Core i5 100% loaded depending on text length.

Program should find correct plugboard (rings and indicators could be wrong because of ring stepping - just ignore it).

You could modify thresholds and ring steps if needed.

### Stage 3 - run full search

Define ring steps, thresholds and how mane top results you need to see from each rotor set.

Call method *searchForAllRotors*.

You may customize method *printResults* to store all results to files. 

***Notice:* if you run search in JetBrains Idea keep in mind that its console holds 5k lines.*

## See also
- [Hillclimbing the Enigma Machine](https://cryptocellar.org/bgac/hillclimb-enigma.pdf) 
by Geoff Sullivan & Frode Weierud, © March 2006
- [Index of Coincidence](https://pages.mtu.edu/~shene/NSF-4/Tutorial/VIG/Vig-IOC.html)

## Authors

- [@asilichenko](https://github.com/asilichenko)

## License

[GNU License LGPL v3.0](LICENSE)
