# Toy Robot Simulator

### Problem description
[PROBLEM](PROBLEM.md)

### How to build
In project directory: 
```sh
$ sbt assembly
```
Artifact is built into target/scala-2.12 directory

### How to run
```
java -jar <path-to>/robot-assembly-1.0.jar
```

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