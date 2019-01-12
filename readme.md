# Advanced Data Structures 

This project has been a part of the [Algav](https://www-master.ufr-info-p6.jussieu.fr/2018/ALGAV) class for the [Sorbonne University: Pierre and Marie Curie](https://www.sorbonne-universite.fr/) students. 

The goal of the project is to graphically visualize the execution time of the algorithms and data structures introduced in the class on some real data.

The following table shows the running time for each data structure and its operations. 

 operations | ArrayMinHeap |BinaryTreeMinHeap|BinomialMinHeap |
--------|-------------|-----------------|--------------- |
Build   | O(n) | O(n) |  O(n) |
Insert  | O(log(n))| O(log(n)) | O(log(n))|
Union | O(n+m) | O(n+m) | O(log(n+m))|
DeleteMin  | O(log(n))| O(log(n)) | O(log(n))|

The data structures store a 128 bit Key. The key is represented by the the interface ``` IKey128 ``` which wraps a BigInterger.

To get the running time of a data structure: see the ``` ExpirementalStudy``` class. 
## Class diagram

![alt text](https://raw.githubusercontent.com/nadirbelarouci/AlgavProject/master/diagram.png)

