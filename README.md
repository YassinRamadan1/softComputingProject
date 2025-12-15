# NFG-Library

A modular, object-oriented **Java soft computing library** implementing:

- **Genetic Algorithms**
- **Fuzzy Logic Systems**
- **Neural Networks**

The library is supported by **real-world case studies**, each demonstrating how a soft computing technique can be applied in practice using a **clean, reusable API**.

---

## 🧠 Overview

**NFG-Library** is designed as a **generic and extensible soft computing framework**.  
Each algorithmic paradigm is implemented **independently of any specific problem**, following clean software architecture principles.

For each project phase, a dedicated **case study** demonstrates:
- How the library is configured
- How data is preprocessed
- How training and evaluation are performed
- How results are obtained and interpreted

---

## 🚀 Features

### 🧬 Genetic Algorithms
- Roulette & rank-based selection  
- Crossover and mutation operators  
- Fitness function abstraction  
- Configurable population size, mutation rate, and generations  
- Problem-independent design  

---

### 🌫️ Fuzzy Logic
- Linguistic variables and fuzzy sets  
- Membership functions (Triangular, Trapezoidal, Gaussian)  
- Mamdani and Sugeno inference engines  
- AND / OR operators (T-Norms & S-Norms)  
- Defuzzification methods (Centroid, Mean of Maximum)  
- Rule-based system with enable/disable and weighting support  

---

### 🧠 Neural Networks
- Feed-forward fully connected neural networks  
- Backpropagation training  
- Activation functions:
  - ReLU  
  - Sigmoid  
  - Tanh  
  - Linear  
- Loss functions:
  - Mean Squared Error (MSE)  
  - Binary Cross-Entropy  
- Optimizer:
  - Stochastic Gradient Descent (SGD)  
- Weight initialization:
  - Xavier initialization  
- Configurable architecture and hyperparameters  
- Graceful handling of invalid or missing inputs  

---

## 🧪 Case Studies

### 🔹 Phase 1 — Genetic Algorithms
**Problem:** Job Scheduling  
**Goal:** Optimize task assignment using evolutionary search  
**Technique:** Genetic Algorithm  

---

### 🔹 Phase 2 — Fuzzy Logic
**Problem:** Automatic Window Blind Control  

**Inputs:**  
- Light Intensity  
- Room Temperature  

**Output:**  
- Blind Opening Percentage  

**Technique:** Mamdani & Sugeno Fuzzy Inference Systems  

---

### 🔹 Phase 3 — Neural Networks
**Problem:** Banknote Authentication  
**Goal:** Classify banknotes as genuine or counterfeit  

**Details:**
- Binary classification  
- Feed-forward neural network  
- ReLU hidden layer + Sigmoid output  
- Cross-Entropy loss  
- Config-driven architecture  

---

## 📚 Technologies Used
- Java 17+
- Maven
- Object-Oriented Design
- Modular Architecture
- Soft Computing Algorithms
- Configuration-driven experiments
