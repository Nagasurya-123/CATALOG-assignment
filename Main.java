package com.mxdui;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * The main method that drives the program.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al Esquema de Secreto Compartido de Shamir");
        System.out.print("Seleccione una opción (c para Cifrar, d para Descifrar): ");
        String opcion = scanner.nextLine();
        byte[] iv = AESCipher.generateIV();

        try {
            switch (opcion) {
                case "c":
                    cifrar(scanner, iv);
                    break;
                case "d":
                    descifrar(scanner);
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    /**
     * Method to encrypt a file using Shamir's Secret Sharing scheme.
     * 
     * @param scanner Scanner to read user input.
     * @param iv      Initialization vector for AES encryption.
     * @throws Exception If an error occurs during encryption.
     */
    private static void cifrar(Scanner scanner, byte[] iv) {
        try {
            System.out.print("Nombre del archivo para guardar las evaluaciones del polinomio: ");
            String archivoEvaluaciones = scanner.nextLine();

            System.out.print("Número total de evaluaciones (n > 2): ");
            int n = Integer.parseInt(scanner.nextLine());

            System.out.print("Número mínimo de puntos para descifrar (1 < t <= n): ");
            int t = Integer.parseInt(scanner.nextLine());

            System.out.print("Nombre del archivo con el documento claro: ");
            String archivoClaro = scanner.nextLine();

            System.out.print("Contraseña para generar la clave de cifrado: ");
            String contraseña = scanner.nextLine();

            byte[] claveCifrado = MessageDigest.getInstance("SHA-256")
                    .digest(contraseña.getBytes(StandardCharsets.UTF_8));

            byte[] datosClaro = FileUtils.readFile(archivoClaro);

            byte[] ivAndEncryptedData = AESCipher.encrypt(datosClaro, claveCifrado, iv);

            String archivoCifrado = archivoClaro + ".aes";
            FileUtils.writeFile(archivoCifrado, ivAndEncryptedData);

            ShamirSecretSharing sss = new ShamirSecretSharing();
            List<BigInteger[]> shares = sss.createShares(new BigInteger(1, claveCifrado), n, t);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoEvaluaciones + ".frg"))) {
                for (BigInteger[] share : shares) {
                    writer.write(share[0] + "," + share[1]);
                    writer.newLine();
                }
            }

            System.out.println("Archivo cifrado con éxito.");
        } catch (NumberFormatException e) {
            System.err.println("Error de formato numérico: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("El archivo no se encontró: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al leer o escribir en el archivo: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algoritmo de cifrado no disponible: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocurrió un error durante el cifrado: " + e.getMessage());
        }
    }

    /**
     * Method to decrypt a file using Shamir's Secret Sharing scheme.
     * 
     * @param scanner Scanner to read user input.
     * @throws Exception If an error occurs during decryption.
     */
    private static void descifrar(Scanner scanner) {
        try {
            System.out.print("Nombre del archivo con las evaluaciones del polinomio: ");
            String archivoEvaluaciones = scanner.nextLine();

            System.out.print("Nombre del archivo cifrado: ");
            String archivoCifrado = scanner.nextLine();

            ShamirSecretSharing sss = new ShamirSecretSharing();
            List<BigInteger[]> shares = sss.readShares(archivoEvaluaciones);

            BigInteger claveCifrado = sss.reconstructSecret(shares);

            byte[] ivAndEncryptedData = FileUtils.readFile(archivoCifrado);

            byte[] iv = Arrays.copyOfRange(ivAndEncryptedData, 0, AESCipher.IV_LENGTH);
            byte[] encryptedData = Arrays.copyOfRange(ivAndEncryptedData, AESCipher.IV_LENGTH,
                    ivAndEncryptedData.length);

            byte[] datosDescifrados = AESCipher.decrypt(encryptedData, claveCifrado.toByteArray(), iv);

            String nombreArchivoOriginal = archivoCifrado.replace(".aes", "");
            FileUtils.writeFile(nombreArchivoOriginal, datosDescifrados);

            System.out.println("Archivo descifrado con éxito.");
        } catch (FileNotFoundException e) {
            System.err.println("El archivo no se encontró: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al leer o escribir en el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocurrió un error durante el descifrado: " + e.getMessage());
        }
    }

}
