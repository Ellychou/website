When I got this test, the pool appears in my mind is database connection pool, which I am using in my daily life, but haven't know much about its implementation details.
Therefore I search and read some ducoment about it. The idea seems not complex, but I find it will be very difficult to make it thread-safe.

Design:
	    I use blockingQueue to put the resources which will be acquired in the future. A blocking queue is a queue that blocks if a thread try to dequeue from it if the queue is empty, or if thread try to enqueue resource to it if it is already full. 

	    I use a volatile modifier for boolean closeCalled as volatile is a special mechanism to guarantee that commmunication happens between threads.

	    I use ConcurrentHashMap to improved the performance while ensuring thread safety. I use CounDownLatch as it will allow one thread to wait for one or more threads before it starts processing.


Thread safe:
	I tried my best to make it "thread-safe", and I abandoned normal Set, List, Map with alternatives from java concurrent package.
	So far, I test a lot, for each API, I ran it in a single thread mode, to make sure it is right, after the tests passed, I also ran each API in multiple threads mode, and make sure all tests passed(actullay, all my tests passed).

	I know it is thread safe as I use the multi thread tasks to test the resource pool.

Furture:
It is pretty chanllanging to finish this test in a short time, and even I tried my best to take all situations into consideration, but I believe I missed some. If I have time, I will study the open-source projects about pool, hopefully, I can understand it better. 