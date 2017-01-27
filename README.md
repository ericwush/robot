# Robot

### How to build
In project directory: 
```sh
$ sbt assembly
```
Artifact is built into scala/target/scala-2.12 directory

### Input

* Valid commands as below
```
PLACE X,Y,F
MOVE
LEFT
RIGHT
REPORT
```

### How to run
```
java -jar <path-to>/robot-assembly-1.0.jar
```

### Description
- The application is a simulation of a toy robot moving on a square tabletop, of dimensions 5 units x 5 units.
- There are no other obstructions on the table surface.
- The robot is free to roam around the surface of the table, but must be prevented from falling to destruction. Any movement that would result in the robot falling from the table must be prevented, however further valid movement commands must still be allowed.
- PLACE will put the toy robot on the table in position X,Y and facing NORTH, SOUTH, EAST or WEST.
- The origin (0,0) can be considered to be the SOUTH WEST most corner.
- The first valid command to the robot is a PLACE command, after that, any sequence of commands may be issued, in any order, including another PLACE command. The application should discard all commands in the sequence until a valid PLACE command has been executed.
- MOVE will move the toy robot one unit forward in the direction it is currently facing.
- LEFT and RIGHT will rotate the robot 90 degrees in the specified direction without changing the position of the robot.
- REPORT will announce the X,Y and orientation of the robot.
- A robot that is not on the table can choose to ignore the MOVE, LEFT, RIGHT and REPORT commands.

### Constraints
- The toy robot must not fall off the table during movement. This also includes the initial placement of the toy robot.
- Any move that would cause the robot to fall must be ignored.

### Test data
```
> PLACE 0,A,NORTH
Invalid arguments for PLACE command
```
```
> MOVE
Command discarded, must first place the robot
```
```
> PLACE -1,0,EAST
> MOVE
> REPORT
```
```
> PLACE 0,0,EAST FOO
Wrong number of argument for PLACE command
```
```
> PLACE 4,4,EAST
> REPORT
4,4,EAST
> MOVE
> REPORT
4,4,EAST
```
```
> PLACE 1,2,EAST
> MOVE
> MOVE
> LEFT
> MOVE
> REPORT
3,3,NORTH
> PLACE 3,2,NORTH
> MOVE
> RIGHT
> REPORT
3,3,EAST
```