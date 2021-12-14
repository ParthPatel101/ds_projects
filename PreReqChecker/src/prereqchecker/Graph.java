package prereqchecker;

import java.util.ArrayList;


import java.util.HashMap;


import java.util.Map;

public class Graph{

    //function
    HashMap<String, Course> courses = new HashMap<String,Course>();
    public Graph(String file){


        courses = new HashMap<String,Course>();
        StdIn.setFile(file);



        int a = StdIn.readInt();

        
        StdIn.readLine();
        for(int i=0;i<a;i++){
           
            String line = StdIn.readLine();
            courses.put(line, new Course(line));
          //  System.out.println(i+" "+line);
        }
        int b = StdIn.readInt();
        StdIn.readLine();
        for(int i=0;i<b;i++){
            courses.get(StdIn.readString()).addPrereq(courses.get(StdIn.readString()));
        }
    }
    public void printGraph(String file){
        StdOut.setFile(file);
        for(Course course:courses.values()){
            StdOut.print(course.name+" ");
            for(Course prereq:course.prereqs){
                StdOut.print(prereq.name+" ");
            }
            StdOut.println();
        }
    }
    static boolean possible = true;
    public void checkPrereq(String file1, String file2){


        StdIn.setFile(file1);


        Course course1 = courses.get(StdIn.readLine());










        Course course2 = courses.get(StdIn.readLine());



        possible = true;
        prereqRecur(course1, course2);
        StdOut.setFile(file2);
        StdOut.println(possible?"YES":"NO");
    }
    public void prereqRecur(Course a, Course b){
        b.completed = true;
        if(b.prereqs.size()>0){
            for(Course prereq:b.prereqs){
                if(!prereq.completed){
                    prereqRecur(a,prereq);
                }
            }
        }
        if(b.equals(a)){



            possible = false;


        }
        
    }
    public void toTake(String file1, String file2){



        StdIn.setFile(file1);
        int num = StdIn.readInt();
        StdIn.readLine();
         for(int i=0;i<num;i++){
            
          
            eligRecur(courses.get(StdIn.readLine()));
        }
        StdOut.setFile(file2);
        for(Course a:courses.values()){
            if(!a.completed){
            boolean canDo = true;


            for(Course b:a.prereqs){
                if(!b.completed){
                    canDo = false;


                    break;
                }
            }
            if(canDo){
                StdOut.println(a.name);
            }
     }
    }

    }
    public void eligRecur(Course b){
       // System.out.println(b.name+" "+b.prereqs.size());
        b.completed = true;
    //    System.out.println(b.name+" "+b.prereqs.size());
        if(b.prereqs.size()>0){
            for(Course prereq:b.prereqs){
                if(!prereq.completed){
                    eligRecur(prereq);
                }
            }
        }
    }
    public void wantTake(String file1, String file2){
        StdIn.setFile(file1);
        Course target = courses.get(StdIn.readLine());
        int d = StdIn.readInt();
        StdIn.readLine();



        for(int i=0;i<d;i++){
            eligRecur(courses.get(StdIn.readLine()));
        }
        StdOut.setFile(file2);
        target.completed=true;


        goThrough(target);
    }
    public void goThrough(Course a){
        if(!a.completed){
            StdOut.println(a.name);
        }
            a.completed=true;
            if(a.prereqs.size()>0){
                for(Course prereq:a.prereqs){
                    if(!prereq.completed){
                        goThrough(prereq);
                    }
                }
            }
        
    }
    public void scheduling(String file1, String file2){
        StdIn.setFile(file1);
        Course target = courses.get(StdIn.readLine());
       // System.out.println(target.name);
        int d = StdIn.readInt();
        StdIn.readLine();
        for(int i=0;i<d;i++){
            String a = StdIn.readLine();
            
            eligRecur(courses.get(a));
        }
        HashMap<Course, Integer> planner= new HashMap<Course, Integer>();





        
        int max = runThrough(planner, -1, target);
        planner.remove(target);
        ArrayList<ArrayList<Course>>diary=new ArrayList<ArrayList<Course>>();
       // System.out.println(max+" "+diary.length);
        for(int i=0;i<=max;i++){
            diary.add(new ArrayList<Course>());
        }
        
        for(Map.Entry<Course, Integer> room:planner.entrySet()){
           diary.get(room.getValue()).add(room.getKey());
           //System.out.println(room.getKey().name+" "+room.getValue());
        }
        StdOut.setFile(file2);










        StdOut.println(diary.size());
       for(int i=diary.size()-1;i>=0;i--){
            for(Course c:diary.get(i)){
                StdOut.print(c.name+" ");
            }
            StdOut.println();
       }
    }
    public int runThrough (HashMap<Course,Integer> planner, int sem, Course cour){
        cour.completed=true;
        int max = sem;

        //done
        planner.put(cour, sem);
        if(cour.prereqs.size()>0){




            
            for(Course prereq:cour.prereqs){   
                if(!prereq.completed||(planner.containsKey(prereq)&&planner.get(prereq)<=sem))
                  max  =Math.max(max, runThrough(planner, sem+1,prereq));
            }
            
        }   
       // System.out.println(cour.name+max);
        return max;
    }
    
    
        
        

    
    
}