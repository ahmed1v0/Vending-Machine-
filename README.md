# Vending Machine Money Simulation

## Project Overview
This project is a simulation of a vending machine system developed in Java. The system tracks money not just as a total sum but also in specific denominations (bills and coins). It ensures that accurate change can be returned to the customer based on the available denominations. The project was implemented as part of an assignment for the position of Associate Java Developer at ProgressSoft Corporation.

## Features
- Tracks money in different denominations (bills and coins).
- Processes customer payments and ensures the correct change is provided.
- Handles edge cases such as insufficient funds or lack of appropriate denominations for change.
- Implements SOLID principles and object-oriented design for maintainability and scalability.
- Developed using Test-Driven Development (TDD) to ensure code reliability through comprehensive unit tests.

## Technologies Used
- **Java**: Core programming language used for developing the simulation.
- **JUnit**: Used for implementing unit tests and following TDD practices.
- **SOLID Principles**: Applied to ensure clean, maintainable, and scalable code.
- **Object-Oriented Design (OOP)**: Employed to structure the system effectively.

## Installation and Usage
1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/vending-machine-money-simulation.git
   cd vending-machine-money-simulation
   ```
2. **Compile and run the program:**
   - Use your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse) or command line.
   - Run the main class to start the simulation.

3. **Run Unit Tests:**
   - The unit tests can be executed using JUnit in your IDE or via the command line.
   ```bash
   ./gradlew test
   ```

## How It Works
- **Money Tracking**: The system tracks the amount of money in the vending machine using specific denominations (e.g., 10 Dinar bills, 1 Dinar coins).
- **Payment Processing**: When a customer selects an item, the system checks if the inserted money is sufficient and whether the correct change can be returned.
- **Change Dispensing**: The system prioritizes returning the most appropriate denominations to match the required change.

## Example Scenario
If the vending machine contains a 10 Dinar bill and a customer requests an item costing 5 Dinars, the system will not dispense change unless it also contains five 1 Dinar bills or equivalent smaller denominations.
