import java.io.*;
import java.util.*;

import static java.lang.Math.*;

public class Closest {
    
    public static class Point{
    int x,y;
    public Point(int x,int y)
        {
        this.x=x;
        this.y=y;
        }
    }
    public static double minimalDistance(int[] x, int y[]) {
        //write your code here
        Point[] points=new Point[x.length];
        for(int i=0;i<x.length;i++)
            points[i]=new Point(x[i],y[i]); //store all points
        Arrays.sort(points,new Comparator<Point>(){  //points sorted by x coordinates
        public int compare(Point p1, Point p2)
            {
            return p1.x-p2.x;
            }
        });
        return distanceUtil(points,0,points.length-1); //main divide and conquer algorithm
    }
    public static double distanceUtil(Point[] points,int i,int j)
    {
        if(j-i+1<=3)    //total points=(startIndex-EndIndex)+1
            return bruteForce(points,i,j);  //for points till 3 we use brute force
        int n=j-i+1;
        int mid=i+(j-i)/2;   //mid point index of points array
        Point midpoint=points[mid];
        double dl=distanceUtil(points,i,mid);   //recursively find closest pair in left subarray
        double dr=distanceUtil(points,mid+1,j); //recursively find closest pair in right subarray
        double d=Math.min(dl,dr);           //minimalDistance between left and right
        List<Point> strip = new ArrayList<>(); //creating the strip
		for (int k = 0; k < n; k++) {
			if (Math.abs(points[k].x - midpoint.x) < d) //only those points in strip whose distance is smaller than d
                strip.add(points[k]);
		}
		return Math.min(d,stripClosest(strip.toArray(new Point[0]),d)); //convert strip to array of type point
    }
    public static double stripClosest(Point[] strip,double d)
    {
        double min=d;
        Arrays.sort(strip,new Comparator<Point>(){  //sort strip points by y coordinates
        public int compare(Point p1,Point p2)
        {
        return p1.y-p2.y;
        }
        });
        for(int i=0;i<strip.length;i++)
        {
        //below loop will execute only 8 times using square hypothesis
        for(int j=i+1;j<strip.length && (strip[j].y-strip[i].y)<min;j++)  //points distance less than d
            {
            double distance=dist(strip[i],strip[j]);
            if(distance<min)
                min=distance;
            }
        }
        return min;
    }
    public static double dist(Point p1, Point p2) {
		return Math.sqrt(Math.pow(Math.abs(p1.x - p2.x), 2) + Math.pow(Math.abs(p1.y-p2.y), 2));
	}
	public static double bruteForce(Point[] p, int start, int end) {
	//System.out.println("brute force start");
		double min = Double.MAX_VALUE;
			for (int i = start; i <=end; i++) {
				for (int j = i+1; j<=end; j++) {
                    //System.out.println("euclidean distance:"+dist(p[i], p[j]));
					min = Math.min(min, dist(p[i], p[j]));
					//System.out.println("min:"+min);
				}
			}
		return min;
	}  
    public static void main(String[] args) throws Exception {
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new PrintWriter(System.out);
        int n = nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = nextInt();
            y[i] = nextInt();
        }
        System.out.println(minimalDistance(x, y));
        writer.close();
    }

    static BufferedReader reader;
    static PrintWriter writer;
    static StringTokenizer tok = new StringTokenizer("");


    static String next() {
        while (!tok.hasMoreTokens()) {
            String w = null;
            try {
                w = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (w == null)
                return null;
            tok = new StringTokenizer(w);
        }
        return tok.nextToken();
    }

    static int nextInt() {
        return Integer.parseInt(next());
    }
}
