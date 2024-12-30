# SSM JSON to .env Converter

This project provides a graphical tool to convert AWS SSM JSON output files into `.env` files for easy use in applications. The tool is implemented in Java and uses Swing for the graphical interface.

## Features

- Upload a JSON file containing SSM parameters.
- Convert the parameters into `.env` format.
- Save the resulting `.env` file to your local system.

## Requirements

- Java 11 or higher.
- `org.json` library (included in the project).

## How to Use

### 1. Compile the Project

Make sure you have the required dependencies and Java installed.

```bash
javac -cp .:lib/json-20230618.jar SSMJsonToEnv.java
```

- Replace `json-20230618.jar` with the actual version of the `org.json` library.
- On Windows, use `;` instead of `:` in the `-cp` argument.

### 2. Run the Program

```bash
java -cp .:lib/json-20230618.jar SSMJsonToEnv
```

### 3. Using the GUI

- Click **Open JSON File** to upload your JSON file.
- Click **Save as .env** to save the converted file.

### 4. Example Input

A sample input JSON file might look like this:

```json
{
  "Parameters": [
    {
      "Name": "/myapp/database/url",
      "Value": "jdbc:mysql://localhost:3306/mydb"
    },
    {
      "Name": "/myapp/database/user",
      "Value": "admin"
    }
  ]
}
```

### 5. Example Output

The `.env` file generated will look like this:

```
url=jdbc:mysql://localhost:3306/mydb
user=admin
```

## Building a JAR File

To package the program into an executable JAR:

1. Create a manifest file `MANIFEST.MF`:
   ```text
   Main-Class: SSMJsonToEnv
   Class-Path: lib/json-20230618.jar
   ```
2. Package the JAR:
   ```bash
   jar cfm SSMJsonToEnv.jar MANIFEST.MF SSMJsonToEnv.class lib/json-20230618.jar
   ```
3. Run the JAR:
   ```bash
   java -jar SSMJsonToEnv.jar
   ```

## Troubleshooting

- Ensure you have Java 11 or higher installed:
  ```bash
  java -version
  ```
- Verify the library `org.json` is included in the `lib` directory.
- If compilation fails, ensure the `CLASSPATH` is correctly set to include the JSON library.

## License

This project is licensed under the MIT License. See the LICENSE file for details.

## Acknowledgments

- **org.json** library for JSON parsing.
- Java Swing for the graphical user interface.

