# Advanced Multithread
<!-- Improved compatibility of Back to Top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--



<!-- PROJECT SHIELDS -->
<!--
-->
[![TheDanielTp][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- ABOUT THE PROJECT -->
## Pi Calculator

### 🔴 Program Overview

The Pi calculator program can calculate the number π up to more than 1,000,000 digits. It leverages multithreading to expedite the calculation process, utilizing the Bailey–Borwein–Plouffe (BBP) formula for term computation.

The program is written in Java and makes use of modern concurrency features to ensure efficient and accurate computations.

### 🟠 Number π

The number π (/paɪ/; spelled out as "pi") is a mathematical constant that is the ratio of a circle's circumference to its diameter, approximately equal to 3.14.

The number π appears in many formulae across mathematics and physics. It is an irrational number, meaning that it cannot be expressed exactly as a ratio of two integers, although fractions such as 22/7 are commonly used to approximate it.

Consequently, its decimal representation never ends, nor enters a permanently repeating pattern. It is a transcendental number, meaning that it cannot be a solution of an equation involving only finite sums, products, powers, and integers.

The transcendence of π implies that it is impossible to solve the ancient challenge of squaring the circle with a compass and straightedge. The decimal digits of π appear to be randomly distributed, but no proof of this conjecture has been found.

### 🟡 Bailey–Borwein–Plouffe formula

The Bailey–Borwein–Plouffe formula (BBP formula) is a formula for π. It was discovered in 1995 by Simon Plouffe and is named after the authors of the article in which it was published, David H. Bailey, Peter Borwein, and Plouffe. Before that, it had been published by Plouffe on his own site.

The formula is:

[![BBP Formula](https://wikimedia.org/api/rest_v1/media/math/render/svg/af6bc360851499dd2ab2a90bee03fbe2040089d5)](https://wikimedia.org/api/rest_v1/media/math/render/svg/af6bc360851499dd2ab2a90bee03fbe2040089d5)

The BBP formula gives rise to a spigot algorithm for computing the nth base-16 (hexadecimal) digit of π (and therefore also the 4nth binary digit of π) without computing the preceding digits. This does not compute the nth decimal digit of π (i.e., in base 10).

But another formula discovered by Plouffe in 2022 allows extracting the nth digit of π in decimal.

BBP and BBP-inspired algorithms have been used in projects such as PiHex for calculating many digits of π using distributed computing. The existence of this formula came as a surprise. It had been widely believed that computing the nth digit of π is just as hard as computing the first n digits.
<p align="right">(<a href="#readme-top">Back to Top</a>)</p>

### 🟢 Key Features and Components

#### Multithreading:

The program determines the number of available processors on the host machine and uses this to set up a fixed thread pool.

Each thread independently computes terms of the BBP series, contributing to the overall sum used to approximate π.

#### Atomic Operations:

An Atomic Reference \<BigDecimal> is employed to maintain the running total of computed terms in a thread-safe manner.
Precision Management:

The precision of the computation is managed via Big Decimal operations. The threshold for terminating the series computation is dynamically set based on the desired number of decimal places.

#### Thread Coordination:

An ExecutorService is used to manage the lifecycle of threads. The program ensures all threads complete their execution within a set time limit (10 minutes).

#### User Interaction:

The main method allows the user to specify the number of decimal places for π through standard input.
The program provides timing information to the user, showing how long the computation took.
<p align="right">(<a href="#readme-top">Back to Top</a>)</p>

### 🔵 Detailed Functionality
#### User Input Loop:

The program continuously prompts the user to enter the desired precision.
If the user enters 0, the program terminates.

#### Performance Measurement:

The time taken for each calculation is measured and displayed.

#### Executor Service Initialization:

A fixed thread pool is created based on the number of available processors.

#### Atomic Sum Initialization:

An AtomicReference with an initial value of 0 is used to accumulate the sum.

#### Threshold Determination:

The threshold for stopping the computation is set as 10^-10

#### Task Submission:

Threads are created to compute terms in parallel. Each thread processes every NUM_THREADS-th term in the series, starting from its threadIndex.

#### Sum Accumulation:

Each thread computes a local sum of terms and adds it to the global sum in a thread-safe manner.

#### ExecutorService Shutdown:

The program waits for all threads to finish, or forcibly shuts down the executor if the time limit is exceeded.

#### Result Calculation:

The final value of π is rounded to the specified precision and returned as a string.

#### Term Calculation:
Utilizes the BBP formula to compute individual terms of the π series.
The formula is applied with additional precision to ensure accuracy during division operations.

### 🟣 Performance and Efficiency
The program is designed to utilize multiple CPU cores effectively, which can significantly reduce the computation time for higher precision calculations of π. The use of Atomic Reference ensures that updates to the global sum are thread-safe, preventing data races and ensuring accuracy.

### ⚪ Conclusion
The Pi Calculator program is a robust implementation for calculating π to a high degree of precision. It combines the strengths of multithreading and precise arithmetic operations in Java. The user-friendly interface and performance metrics provide a comprehensive tool for both educational purposes and practical applications requiring the value of π.

<p align="right">(<a href="#readme-top">Back to Top</a>)</p>

<!-- GETTING STARTED -->
## Semaphore

### 🔴 Program Overview

The semaphore-based resource access program is a Java application designed to demonstrate how semaphores can be used to control concurrent access to a shared resource. This is particularly important in multi-threaded environments where managing resource contention and ensuring data consistency are critical. The program includes three main classes: Controller, Operator, and Resource.

### 🟠 Key Components
#### Controller Class:

Purpose: Initializes the application and manages the creation and execution of Operator threads.

Semaphore: A semaphore with two permits is created to limit the number of threads that can access the critical section simultaneously.

Main Method: Five Operator threads are instantiated and started, each attempting to access the shared resource.

#### Operator Class:

Purpose: Represents a thread that attempts to access the shared resource.
Constructor: Initializes the thread with a unique name.

#### Run Method:
Acquire Permit: Each thread tries to acquire a permit from the semaphore before entering the critical section.

Resource Access: The thread calls the Resource.accessResource() method, simulating the usage of the shared resource.

Logging: The thread logs the time it accessed the resource.
Release Permit: After accessing the resource, the thread releases the permit, allowing other threads to enter the critical section.

#### Resource Class:

Purpose: Simulates the shared resource that threads are trying to access.
Access Method: Implements a delay (1 second sleep) to simulate some work being done with the resource.

### 🟡 How It Works
Semaphore Mechanism: The semaphore initialized with two permits allows up to two threads to enter the critical section concurrently. If more than two threads attempt to enter, they will wait until a permit becomes available.

Thread Operations:
Each Operator thread acquires a semaphore permit before accessing the Resource.
The Resource.accessResource() method simulates a delay, representing the resource being used.
After accessing the resource, the thread logs the access time and releases the semaphore permit.
Concurrency Control: By using a semaphore, the program ensures that no more than two threads can access the resource at the same time, preventing race conditions and managing resource contention efficiently.

### 🟢 Benefits
Efficient Resource Management: By limiting concurrent access to the resource, the program ensures efficient and controlled usage.

Data Consistency: The use of semaphores prevents race conditions, ensuring that shared data remains consistent.

Real-World Simulation: The program simulates a realistic scenario where multiple threads contend for limited resources, a common situation in many applications.


<!-- LICENSE -->
## 🔵 License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">Back to Top</a>)</p>



<!-- CONTACT -->
## 🟣 Contact

My Email - prof.danial4@gmail.com

Project Link: [https://github.com/TheDanielTp/2048](https://github.com/TheDanielTp/2048)

<p align="right">(<a href="#readme-top">Back to Top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/TheDanielTp/2048.svg?style=for-the-badge
[contributors-url]: https://github.com/TheDanielTp/2048/graphs/contributors
[Java-url]: https://www.java.com/en/
[Java.image]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[forks-shield]: https://img.shields.io/github/forks/TheDanielTp/2048.svg?style=for-the-badge
[forks-url]: https://github.com/TheDanielTp/2048/network/members
[stars-shield]: https://img.shields.io/github/stars/TheDanielTp/2048.svg?style=for-the-badge
[stars-url]: https://github.com/TheDanielTp/2048/stargazers
[issues-shield]: https://img.shields.io/github/issues/TheDanielTp/2048.svg?style=for-the-badge
[issues-url]: https://github.com/TheDanielTp/2048/issues
[license-shield]: https://img.shields.io/github/license/TheDanielTp/2048.svg?style=for-the-badge
[license-url]: https://github.com/TheDanielTp/2048/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/linkedin_username
[product-screenshot]: images/screenshot.png
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white
[Laravel-url]: https://laravel.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com 
