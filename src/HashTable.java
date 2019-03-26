import java.util.*;
/**
 * 
 * @author Yu Xin
 * @email yxin29@wisc.edu
 * @ID 9081207145
 * @way use bucket and list node
 * @hash_algorithm 
 * 
 * insert the key
 * use this key to get a hash number by using hashcode
 * get the index of the table
 * find whether it is duplicate
 * if not insert it
 * after insert to judge whether table need to be resized
 * 
 * remove
 * to find the key and then remove it from its bucket
 * if it isn't exist return false
 * 
 * get
 * get it index of the table
 * then find it in the table[index]
 * if exist return return it value
 * else throw an exception
 * 
 * other return some relative value by fuction
 */

public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
	// create a class to store information
	public class Node<K extends Comparable<K>, V>{
		K key;
		V value;
		Node(K key,V value){
			this.key = key;
			this.value = value;
		} 
	}
	// TODO: ADD and comment DATA FIELD MEMBERS needed for your implementation
	private LinkedList<Node>[] table;// hashtable to store different hash information
	private int size; // the key number in table
	private int maxsize; // the size the need to resize
	private double loadFactorThreshold;// the coefficient

	// TODO: comment and complete a default no-arg constructor
	public HashTable() {
		table = new LinkedList[11]; // the original table size is 11
		for(int i = 0;i < 11;i++) // initialize
			table[i] = new LinkedList<Node>();
		maxsize = (int)(0.75 * 11);
		loadFactorThreshold = 0.75;
		size = 0; // original size is 0, beacuse it is empty
	}
	
	// TODO: comment and complete a constructor that accepts 
	// initial capacity and load factor threshold
    // threshold is the load factor that causes a resize and rehash
	
	// make a hashtable which has coefficient
	public HashTable(int initialCapacity, double loadFactorThreshold) {
		table = new LinkedList[initialCapacity];
		for(int i = 0;i <initialCapacity;i++)
			table[i] = new LinkedList<Node>();
		maxsize = (int)(initialCapacity * loadFactorThreshold);
		size =0 ;
		this.loadFactorThreshold = loadFactorThreshold;
	}
	// use hashcode to get a unique hash number
	private int hashkey(K key) {
		return Math.abs(key.hashCode());
	}
	// to get the key position of hashtable
	private int get_index(int hash, LinkedList<Node>[] t) {
		return hash%(t.length);
	}
	// to make table size double and + 1 then make every node in a new position
	private void reset_size() {
		LinkedList<Node>[] oldt = table;// store old table
		int upsize = 2 * table.length + 1;
		LinkedList<Node>[] newt = new LinkedList[upsize];// make a new table
		for(int i = 0;i < upsize;i++) //initial the table
			newt[i] = new LinkedList<Node>();
		change_table(oldt,newt); // make every node in correct position in new table
		table = newt;
		maxsize = (int)(upsize * loadFactorThreshold);// update new maxsize
		return;
 	}
	// put all element in oldtable into new table
	private void change_table(LinkedList<Node>[] oldt,LinkedList<Node>[] newt) {
		for(int i = 0;i < oldt.length;i++) {
			for(int j = 0;j < oldt[i].size();j++) {
				Node<K,V> tmp = oldt[i].get(j);
				int index = get_index(hashkey(tmp.key),newt);
				newt[index].add(tmp);
			}   
		}
	}
	// insert a node into table
    public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException{
    	if(key == null) throw new IllegalNullKeyException();
    	int hash = hashkey(key);
    	int index = get_index(hash,table);
    	for(int i = 0;i < table[index].size();i++) {// find whether duplicate
    		if(table[index].get(i).key.equals(key)) {
    			throw new DuplicateKeyException();
    		}
    	}
    	// there is not duplicate
    	Node p = new Node(key,value); // put node into table
    	table[index].add(p);
    	size = size + 1;
    	if(size >= maxsize) {
    		reset_size();
    	}
    }
    // remove a node from table
    public boolean remove(K key) throws IllegalNullKeyException{
    	if(key == null) throw new IllegalNullKeyException();// if key == null throw IllegalNullKeyException
    	int hash = hashkey(key);
    	int index = get_index(hash,table); // get position
    	for(int i = 0;i < table[index].size();i++) { // find this node
    		if(table[index].get(i).key.equals(key)) {
    			table[index].remove(i);// remove it from table
    			size = size - 1; // make size = size - 1
    			return true;
    		}
    	}
    	return false;
    }

    public V get(K key) throws IllegalNullKeyException, KeyNotFoundException{
    	if(key == null) throw new IllegalNullKeyException();// if key == null throw IllegalNullKeyException
    	int hash = hashkey(key);
    	int index = get_index(hash,table); // get position
    	for(int i = 0;i < table[index].size();i++) { // find this node
    		Node<K,V> p = table[index].get(i);
    		if(p.key.equals(key)) {// find!
    			return (p.value); // return its value
    		}
    	}
    	throw new KeyNotFoundException();//did not find throw a KeyNotFoundException;
    }
    // return the size of element in table
    public int numKeys() {
    	return this.size;
    }
    
    // return the  coefficient of this table
    public double getLoadFactorThreshold() {
    	return loadFactorThreshold;
    }
    
    // return the load factor of this table
    public double getLoadFactor() {
    	return ((double)size)/table.length;
    }
    // return the table size
    public int getCapacity() {
    	return table.length;
    }
    // use bucket and list node so return 5
    public int getCollisionResolution() {
    	return 5;
    }

		
}
