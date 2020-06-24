//Joseph Bdaro CS324-34

import java.io.*;
import java.util.*;

//listnode class
class listNode {
    private String data = new String();
    private listNode next;

    //default constructor
    listNode(){
        data = "dummy";
        next = null;
    }

    //constructor
    listNode(String data){
        this.data = data;
        next = null;
    }

    //prints a node you pass to it to a specified text file
    public static void printNode(listNode node, File outFile) throws IOException {
        listNode iterator = node;
        FileWriter fileWriter = new FileWriter(outFile, true);
        PrintWriter writer = new PrintWriter(fileWriter);

        writer.print("(" + iterator.getData() + ", " + iterator + ", " + iterator.getNext() + ", " + iterator.getNext().getData() + ")");
    }

    //getters and setter to keep our node class data secure
    public String getData() {
        return data;
    }

    public listNode getNext() {
        return next;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setNext(listNode next) {
        this.next = next;
    }
}

//LL class
class linkedList {

    //default contructor
    linkedList(listNode listHead, File inFile, File outFile2) throws IOException {

        //lets us read a from a specified file and then calls the insert node method as well as prints how the list looks like with each insert to a file
        String data = new String();
        Scanner reader = new Scanner(new FileInputStream(inFile));


        while (reader.hasNextLine()) {
            data = reader.next();
            listNode newNode = new listNode(data);
            listInsert(listHead, newNode);
            printList(listHead, outFile2);
        }
        reader.close();
    }

    //inserts a newnode from the file to the LL
    public void listInsert(listNode listHead, listNode newNode) {
        listNode spot = findSpot(listHead, newNode);
        newNode.setNext(spot.getNext());
        spot.setNext(newNode);
    }

    //finds the correct spot to insert
    public listNode findSpot(listNode listHead, listNode newNode) {
        listNode spot = listHead;
        while (spot.getNext() != null && ifSpotsNextLessThanNn(spot, newNode) == true) {
            spot = spot.getNext();
        }
        return spot;
    }

    //this let's us compare strings using the compareTo method to be able to find out if spot.next.data < newNode.data
    private boolean ifSpotsNextLessThanNn(listNode spot, listNode newNode){
        if(spot.getNext().getData().compareTo(newNode.getData()) < 0){
            return true;
        }
        else{
            return false;
        }
    }

    //prints the current LL to a file
    public void printList(listNode listHead, File outFile) throws IOException {
        listNode iterator = listHead;
        FileWriter fileWriter = new FileWriter(outFile, true);
        PrintWriter writer = new PrintWriter(fileWriter);

        //initially always prints the dummy head node first
        writer.print("ListHead ->(" + iterator.getData() + ", " + iterator + ", " + iterator.getNext() + ", " + iterator.getNext().getData() + ")");
        iterator = iterator.getNext();

        //starts iterating and printing the list until it gets to the final node
        while(iterator.getNext() != null){
            writer.print("->(" + iterator.getData() + ", " + iterator + ", " + iterator.getNext() + ", " + iterator.getNext().getData() + ")");
            iterator = iterator.getNext();
        }

        //due to java not printing null pointers this printline will print the last node with the node.getNext pointing to null and and node.getNext.getData also pointing to null
        writer.print("->(" + iterator.getData() + ", " + iterator + ", " + "NULL" + ", " + "NULL" + ")");
        writer.println("\n");
        writer.close();
    }

    //not used but if called will print the middlenode of the current LL to a file
    public listNode findMiddleNode(listNode listHead, File outFile2) throws IOException {
        listNode walk1 = listHead;
        listNode walk2 = listHead;
        listNode.printNode(walk1, outFile2);
        while(walk2 != null && walk2.getNext() != null){
            listNode.printNode(walk1, outFile2);
            walk1 = walk1.getNext();
            walk2 = walk2.getNext().getNext();
        }
        return walk1;
    }

    //reverses the Linkedlist
    public void LLreverse(listNode listHead, File outFile) throws IOException {
        listNode last = listHead.getNext();

        while (last.getNext() != null) {
            listNode spot = last.getNext();
            moveSpotNodeToFront(listHead, spot);
            printList(listHead, outFile);
        }


    }
    //moves the spot node to the front of the list
    public void moveSpotNodeToFront(listNode listHead, listNode spot){
        while (listHead.getNext() != spot) {
            listNode prev = listHead.getNext();
            listNode beforePrev = prev;
            while (prev.getNext() != spot) {
                 beforePrev = prev;
                prev = prev.getNext();
            }
            if(listHead.getNext().getNext() == spot) {
                prev.setNext(spot.getNext());
                spot.setNext(prev);
                listHead.setNext(spot);
            }

            else{
                prev.setNext(spot.getNext());
                spot.setNext(prev);
                beforePrev.setNext(spot);
            }

        }
    }

}


public class Main {

    public static void main(String[] args) throws IOException {
        File inFile = new File(args[0]);
        File outFile1 = new File(args[1]);
        File outFile2 = new File(args[2]);

        //checks if outFile1 and outFile2 exists, if so then we will overwrite them
        if (outFile1.exists()){
            outFile1.delete();
        }
        if (outFile2.exists()){
            outFile2.delete();
        }

        outFile1.createNewFile();
        outFile2.createNewFile();

        listNode listHead = new listNode();
        linkedList constructLL = new linkedList(listHead, inFile, outFile2);
        constructLL.printList(listHead, outFile1);
        constructLL.LLreverse(listHead, outFile2);
        constructLL.printList(listHead, outFile1);

    }
}
