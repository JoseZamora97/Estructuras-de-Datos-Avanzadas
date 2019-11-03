package practicas.practica3.usecase;


import java.util.Deque;
import java.util.LinkedList;

import practicas.practica3.bynarytree.BinaryTree;
import practicas.practica3.bynarytree.LinkedBinaryTree;


/**
 * Practoca 3 Ejercicio 3 Resolucion.
 *
 * @author Jose Miguel Zamora Batista.
 */
public class MorseTranslator {

    /**
     * Generates a new MorseTranslator instance given two arrays:
     * one with the character set and another with their respective
     * morse code.
     *
     * @param charset
     * @param codes
     */

    BinaryTree<MorseNode> tree;
    Deque<MorseNode> p;

    public MorseTranslator(char[] charset, String[] codes) {
         tree = new LinkedBinaryTree<>();

         p = new LinkedList<>();
         tree = new LinkedBinaryTree<>();

         for( int i = 0; i<charset.length; i++)
             p.addLast(new MorseNode(charset[i], codes[i]));

    }

    /**
     * Decodes a String with a message in morse code and returns
     * another String in plaintext. The input String may contain
     * the characters: ' ', '-' '.'.
     *
     * @param morseMessage
     * @return a plain text translation of the morse code
     */
    public String decode(String morseMessage) {
        throw new RuntimeException("Not yet implemented");
    }


    /**
     * Receives a String with a message in plaintext. This message
     * may contain any character in the charset.
     *
     * @param plainText
     * @return a morse code message
     */
    public String encode(String plainText) {
        throw new RuntimeException("Not yet implemented");
    }

    private class MorseNode {
        char c;
        String code;

        private MorseNode (char c, String code) {
            this.c = c;
            this.code = code;
        }
    }

}
