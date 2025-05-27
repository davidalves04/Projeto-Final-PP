/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Utilizador
 */
public class JsonAccumulator {
      private final StringBuilder sb = new StringBuilder();

    public void append(String jsonLine) {
        sb.append(jsonLine).append("\n");
    }

    public String getJson() {
        return sb.toString();
    }
}
