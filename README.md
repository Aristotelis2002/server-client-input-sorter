# Server-client project that sorts array inputs
## How to run it with Maven
First clone the repo.    
Open with a terminal the folder "server-client-input-sorter" that you just cloned.   
To start the server, run the command   
``` mvn exec:java -Dexec.mainClass="server.Server" ```  
To start the client, simply run  
``` mvn exec:java ```

## Documentation
### Introduction
The project's idea is to function as a multi-threaded server that can serve multiple clients simultaneously. Clients can send requests to the server, which are processed and the result of the input gets sent back to the client. Queries consist of the type of sorting algorithm, the number of threads (if the algorithm is multithreaded), and the array which the client wants sorted.  
### Basic concepts and algorithms
Four sorting algorithms are currently implemented in the project: single-threaded merge sort, multi-threaded merge sort (which works with a dynamic number of threads), single-threaded quick sort and multi-threaded optimized quick sort (which works with the number of threads submitted by the client).  
Five threads are reserved for the server to serve clients and their requests. These 5 threads can spawn additional threads as needed for a given request. The number of dedicated threads (5) limits the number of clients that can be served simultaneously.
### Implementation
Java offers an extensive toolkit around multi-threaded code that is versatile and easy to use.  
The Server class is implemented using the built-in Socket class for the network part of the project and through the ExecutorService class, which is initialized with a fixed thread pool (5 threads). When a client connects via the socket, it is accepted by the server and an object of the ClientHandler class is created. This object is added to a queue (implicitly). This queue is processed by the fixed 5 threads, where each thread takes a task to perform.  
Multithreaded merge sort is implemented using the ForkJoinPool framework. This framework is useful because it allows "work-stealing", which balances work between threads.  
Multithreaded quick sort is implemented with the same framework, but with a fixed number of threads submitted by the client. For arrays with fewer than 10_000 elements, no additional threads are allocated for the slices, and one thread sorts the entire array. This is done with the goal of saving time and resources, because the creation of threads and their pausing, termination takes a lot of time(those are Kernel calls that are expensive).  
### Test results
Given an array of 10,000,000 elements. The time on the right is how long the given array was sorted with the particular algorithm.  
Single-threaded mergesort -> 205ms  
Multithreaded mergesort -> 136ms  
Single-threaded quicksort -> 110ms  
Multithreaded quicksort with 4 threads -> 70ms  
Multithreaded quicksort with 8 threads -> 32ms  
The anomaly regarding the results of single-threaded qs and multi-threaded ms is most likely due to the fact that meregesort itself cannot be in-place unlike quicksort, which means it has to dynamically allocate and copy memory, which slows down the algorithm.  
### Conclusion
This project gives the opportunity to study the performance of different algorithms from multiple clients in parallel. The project has a simple hierarchy, which allows the project itself to be easily upgraded. In the future, it would be good to have more comparison algorithms and to implement the server itself using a selector instead of a fixed thread pool.