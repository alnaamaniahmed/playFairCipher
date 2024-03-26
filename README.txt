SOLVED THIS EXERCISE: 
Write a program to simulate the Playfair substitution cipher. You can implement your program in
Java, C, C++, or Python.
Your program should have three methods/functions:
Method 1: Accepts a secret key (String of characters) as argument and generates and returns a key matrix (2D
array).
Method 2: Accepts plaintext (String of characters) and the key matrix as arguments and generates and returns
the ciphertext.
Method 3: Accepts ciphertext (String of characters) and the key matrix as arguments and generates and returns
the plaintext.
You may assume that the plaintext, ciphertext and the key all consist of only upper-case letters. You can ignore
spaces, punctuations, and line breaks.
Use a driver program with a main method to test the above methods. First test your code using the examples
from the lecture notes. Ensure that you test some of the special cases such as repeated characters in a pair, odd
total number of characters, etc.
Next, run your program to encrypt the following text, using the secret key RAYQUAZA
POKEMON TOWER DEFENSE
YOUR MISSION IN THIS FUN STRATEGY TOWER DEFENSE GAME IS TO HELP PROFESSOR OAK TO STOP ATTACKS
OF WILD RATTATA. SET OUT ON YOUR OWN POKEMON JOURNEY, TO CATCH AND TRAIN ALL POKEMON AND
TRY TO SOLVE THE MYSTERY BEHIND THESE ATTACKS. YOU MUST PLACE POKEMON CHARACTERS
STRATEGICALLY ON THE BATTLEFIELD SO THAT THEY STOP ALL WAVES OF ENEMY ATTACKER
DURING THE BATTLE YOU WILL LEVEL UP AND EVOLVE YOUR POKEMON. YOU CAN ALSO CAPTURE OTHER
POKEMON DURING THE BATTLE AND ADD THEM TO YOUR TEAM. USE YOUR MOUSE TO PLAY THE GAME.
GOOD LUCK
Note: Ignore spaces, punctuations, and line breaks.
Decrypt the ciphertext to get back the above plaintext (ignore about spaces, punctuations, and line breaks).
As an example, when you encrypt and decrypt, the text will appear as below:
POKEMON TOWER DEFENSE YOUR MISSION … (Plaintext)
à
xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx … (Ciphertext; x stands for some
uppercase letter)
à
POKEMONTOWERDEFENSEYOURMISSION … (Ciphertext back to Plaintext; the plaintext may have
extra Q, X and Z characters – you can leave them as they are)