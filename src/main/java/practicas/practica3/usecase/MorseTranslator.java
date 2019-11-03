package practicas.practica3.usecase;


import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

import practicas.Position;
import practicas.practica3.bynarytree.LinkedBinaryTree;
import practicas.practica3.iterators.InorderBinaryTreeIterator;

/**
 * Practoca 3 Ejercicio 3 Resolucion.
 *
 * @author Jose Miguel Zamora Batista.
 */
public class MorseTranslator {


    private LinkedBinaryTree<Character> morseTree;

    /**
     * Generates a new MorseTranslator instance given two arrays:
     * one with the character set and another with their respective
     * morse code.
     *
     * @param charset conjunto de letras
     * @param codes codigos morse
     */
    public MorseTranslator(char[] charset, String[] codes) {
        morseTree = new LinkedBinaryTree<>();
        morseTree.addRoot('\0');

        init(charset, codes);
    }

    private void init(char[] charset, String[] codes) {
        for (int i = 0; i < charset.length; i++){
            Position<Character> position = morseTree.root();
            for (int k = 0; k < codes[i].length(); k++){
                if (codes[i].charAt(k) == '.') {
                    if (morseTree.hasLeft(position))
                        position = morseTree.left(position);
                    else
                        position = morseTree.insertLeft(position, '\0');
                }
                else {
                    if (morseTree.hasRight(position))
                        position = morseTree.right(position);
                    else
                        position = morseTree.insertRight(position, '\0');
                }
            }
            morseTree.replace(position, charset[i]);
        }

    }

    /**
     * Decodes a String with a message in morse code and returns
     * another String in plaintext. The input String may contain
     * the characters: ' ', '-' '.'.
     *
     * @param morseMessage mensaje a decodificar
     * @return a plain text translation of the morse code
     */
    public String decode(String morseMessage) {
        StringBuilder output = new StringBuilder();

        for (String messagePiece : segmentateMessage(morseMessage)) {
            Position<Character> position = morseTree.root();

            if(messagePiece.equals(""))
                output.append(" ");

            for(int i = 0; i < messagePiece.length(); i++) {

                char symbol = messagePiece.charAt(i);

                if(morseTree.isInternal(position)) {
                    if(symbol == '.')
                        position = morseTree.left(position);
                    else
                        position = morseTree.right(position);
                }

                if(i == messagePiece.length() - 1)
                    output.append(position.getElement());
            }
        }

        return output.toString();
    }

    private Deque<String> segmentateMessage(String morseMessage) {
        Deque<String> queueMessages = new LinkedList<>(Arrays.asList(morseMessage.split(" ")));
        Deque<String> output = new LinkedList<>();

        while(!queueMessages.isEmpty()) {
            String message = queueMessages.removeFirst();
            if(message.length() < 3)
                output.addLast(message);
            else {
                if(message.startsWith("-.")) {
                    output.addLast("-.");
                    message = message.substring(2);
                }
                while(true){
                    if(message.length() > 3) {
                        output.addLast(message.substring(0, 3));
                        message = message.substring(3);
                    }
                    else
                        break;
                }
                output.addLast(message);
            }
        }

        return output;
    }


    /**
     * Receives a String with a message in plaintext. This message
     * may contain any character in the charset.
     *
     * @param plainText texto a codificar
     * @return a morse code message
     */
    public String encode(String plainText) {
        char[] charsText = plainText.toLowerCase().toCharArray();
        StringBuilder output = new StringBuilder();
        Position<Character> node = null;

        for(char c : charsText) {
            if(c == ' ') {
                output.append(" ");
                continue;
            }

            InorderBinaryTreeIterator<Character> it
                    = new InorderBinaryTreeIterator<>(morseTree);

            boolean found = false;

            while(it.hasNext() && !found) {
                node = it.next();
                if(node.getElement() == c)
                    found = true;
            }

            output.append(getMorseCode(node));
        }

        return output.toString();
    }

    private String getMorseCode(Position<Character> node) {
        StringBuilder output = new StringBuilder();
        while(node != morseTree.root()) {
            if(morseTree.left(morseTree.parent(node)) == node)
                output.append(".");
            if(morseTree.right(morseTree.parent(node)) == node)
                output.append("-");
            node = morseTree.parent(node);
        }

        output.reverse();

        if(output.length() < 3 && !output.toString().equals("-."))
            output.append(" ");

        return output.toString();
    }

}
