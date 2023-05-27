Things to consider:
- you return true in all cases in the movement() function
	- this results in the program always saying "maze solved" when 
	  in reality it wasn't
	- I FIXED THIS

	
- your logic to mark the deadend paths is incorrect
	- FIRSTLY:
		- your logic states that you look at all row numbers between
		  saveRow and numRow, and within each of those rows, you look
		  at each column between saveCol and numCol
		- this is problematic when saveRow == numRow, but saveCol != numCol
	- SECONDLY:
		- you start your iteration of marking the incorrect path at
		  saveRow and saveCol
		- you should really start marking at your current position
		  (numRow and numCol), and then stop once you reach saveRow
		  and saveCol


- saveCol and saveRow only a single multiple-path-position
	- in the maze, if you are going along a certain path, there are many
	  positions in that path where there are forks (when I got your program
	  to print its path before failing, I counted 6 different forks)
	- according to your implementation of multiplePaths() function, saveCol
	  and saveRow only save the most recent fork in your current path
	- imo, it is not worth saving every single forking position
		- doing so is cumbersome (you would have to create an array of size
		  numRow * numCol to store possible fork positions, assuming worst case)
		- saving these positions does not make sense in the essence of recursive
		  back tracking
			- recursive function calls are pushed to the call stack
			- below is a crude example; to understand this example, note that
			  every indentation is the function calling itself and pushing a new
			  call to the stack--it either returns SPATH, FORK, or DEAD
		    - when indentations are deleted, that means stack calls are being popped
			  (the call to that function has returned a value)
		    - SPATH means that the function was called and it found a single possible
			  path, FORK means that the function was called and it found multiple paths,
			  and DEAD means that the function met a deadend

SPATH
	SPATH
		FORK
			SPATH
				DEAD
			DEAD
		SPATH
			SPATH
				...

- NOTE: your movement() function checks for ALL possible directions of movement 
  (so you have to check for up, down, left, and right movement; just like how you
  have it in your code right now)
	- if only 1 direction can be used to move, then there is a SPATH (single path)
	- if multiple directions be used to move, then there is a FORK (multiple paths)
	- if there are no moves available, then the current position DEAD (dead end)
	- notice in the above example, we eventually reach a DEAD
		- a function call before the DEAD only found a single path
		- if this single path lead to a dead end, then the position that
		  the function call was made from would also lead to a dead end;
		  the function call also returns DEAD
	- eventually, the DEAD paths reach the FORK
		- in the FORK, only 1 of 4 possible conditions have failed
		- we have defined FORK to be a situation in which there is more than 1
		  possible direction to move from
		- so therefore, the other directions would be checked, and we would move
		  towards the next open path we come across in that position
		- in this specific example, there were only 2 open paths--1 was found to be
		  a dead end, so there is only 1 path left (SPATH)
	- NOTE: in this example, we did not have to save the positions that have multiple
	  paths--our implementation of movement() and that fact that it is recursive
	  takes care of this for us
	