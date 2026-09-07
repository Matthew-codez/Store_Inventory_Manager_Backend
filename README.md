# Redesigned_Store_Inventory_Manager

A desktop application for managing store inventory, orders, and customer records, built for internal staff use. The system pairs a Spring Boot REST API backend with a Java Swing desktop client, allowing employees to log in and manage stock, orders, and customer information.

Features
Employee authentication: Staff can register and log in before accessing the system.
Inventory management: View, add, and delete stock items, with fields for product, category, supplier, quantity, unit price, and storage location.
Order management: View, add, and delete orders, including order date, delivery date, total amount, status, and item.
Customer management: View, add, and delete customer records, including contact and address details.
Architecture

The backend is built with Spring Boot and Spring Data JPA, exposing REST endpoints for each domain area. The frontend is a Java Swing application that communicates with the backend over HTTP using Java's built-in HttpClient and Jackson for JSON serialization.

Tech Stack
Java
Spring Boot / Spring Data JPA
Java Swing (desktop client)
Jackson (JSON mapping)
