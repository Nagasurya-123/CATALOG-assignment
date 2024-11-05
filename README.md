# User Manual: Shamir's Shared Secret Scheme



## Introduction

This manual describes the implementation and use of the Shamir Shared Secret Scheme, a tool designed to encrypt and decrypt sensitive files using a collaborative approach.

## Operation

Shamir's Shared Secret Scheme allows hiding a secret key K within a polynomial of degree \(t-1\). This polynomial is evaluated at \(n\) different points, generating fragments that can be distributed among authorized users. To recover the key K, at least \(t\) of these fragments are needed.

## Installation and Requirements

The program requires free software libraries to handle AES encryption, SHA-256, and large integer manipulation. Make sure you have these libraries installed before proceeding.

## How to Run the Program

To use the Shamir Shared Secret Scheme, follow these steps:

1. **Compile the Project**:

   - Open a terminal or command line.
   - Navigate to the project directory.
   - Run the command `mvn package`. This will compile the project and create a JAR file in the `target` directory.

2. **Run the Program**:
   - Once the project is compiled, run the program with the command `java -jar target/ShamirSecretSharingScheme-1.0-SNAPSHOT.jar`.
   - Follow the on-screen instructions to select whether to encrypt or decrypt a file.

## How to Run the Tests

To run the unit tests included in the project:

1. **Run Tests**:
   - Open a terminal or command line.
   - Navigate to the project directory.
   - Run the command `mvn test`. This will run all unit tests defined in the project.
   - Maven will report the results of the tests in the terminal.

## Additional Notes

- Make sure you have Maven installed and configured correctly on your system.
- Maven commands (`mvn`) must be run in the root directory of the project, where the `pom.xml` file is located.
- If you make changes to the code, you will need to recompile the project with `mvn package` before running the program again.

## Use of the Program

### Encryption Mode

To encrypt a file:

1. Run the program with the `c` option.
2. Provide the name of the file to save the \(n\) polynomial evaluations.
3. Specify the total number of evaluations required (\(n > 2\)).
4. Indicate the minimum number of points needed to decipher (\(1 < t \leq n\)).
5. Enter the file name with the clear document.
6. During execution, the program will ask for a password that will be used to generate the encryption key.

### Decryption Mode

To decrypt a file:

1. Run the program with the `d` option.
2. Provide the name of the file that contains at least \(t\) of the \(n\) evaluations of the polynomial.
3. Enter the name of the encrypted file.

## Program Outputs

- **Encrypted Mode**: Two files are generated: one with the encrypted document and another with the \(n\) pairs \((x_i, P(x_i))\) of the polynomial evaluations.
- **Decryption Mode**: The document is recovered in its original form.

## Additional Considerations

- The program is designed to be robust and efficient.
- Each function is documented to explain its purpose, parameters and results.
- Unit tests are included for each module.

## Implementation

The program is a practical and secure implementation of the Shamir Shared Secret Scheme, focused on the protection of confidential information through a collaborative approach. Its design allows authorized users to access encrypted information securely and efficiently, as long as the minimum requirements for collected fragments are met.