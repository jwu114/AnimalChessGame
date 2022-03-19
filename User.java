package MainGame;


import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable 
{
    private String userName;
    private String password;
    private int[] fragments;
    private int[] alive;        //record alive times of each chess
    private int[] tools;
    /*
     * 0 ~ 7 are the position check of your different chesses
     */
    private ArrayList<String> friendsName;
    
    private int coins;
    private int gameTimes;      //times of playing
    private int winnerTimes;    //times of being winner
    private float winningRate;
    public User(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
        coins = 1000;
        gameTimes = 0;
        winnerTimes = 0;
        fragments = new int[8];
        alive = new int[8];
        tools = new int[8];
        friendsName = new ArrayList<String>();
        initialize();        
    }
    
    public void initialize(){
        for(int i = 0; i < fragments.length; i++){
            fragments[i] = 0;
        }
        for(int i = 0; i < alive.length; i++){
            alive[i] = 0;
        }
        for(int i = 0; i < tools.length; i++){
            tools[i] = 0;
        }
        calWinningRate();
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword(){
        return password;
    }
    
    public int getCoins(){
        return coins;
    }
    
    public int getGameTimes(){
        return gameTimes;
    }
    
    public int getWinnerTimes(){
        return winnerTimes;
    }
    
    public float getWinningRate(){
        return winningRate;
    }
    
    public int[] getTools(){
        return tools;
    }
    
    public int[] getAliveTimes(){
        return alive;
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPassword(String password){
        this.password = password;
    }
    
    public void setCoins(int coins){
        this.coins = coins;
    }
    
    public void setGameTimes(int gameTimes){
        this.gameTimes = gameTimes;
    }
    
    public void setWinnerTimes(int winnerTimes){
        this.winnerTimes = winnerTimes;
    }
        
    public void setFragment(int index, int num){
        fragments[index] = num;
    }
    
    public void setAliveTimes(int index, int num){
        alive[index] = num;
    }
    
    public void setToolNum(int index, int num){
        tools[index] = num;
    }    
    
    public void addFriends(String newFriend){
        friendsName.add(newFriend);
    }
    
    public boolean removeFriends(String friend){
        boolean flag = false;
        int index = 0;
        while(index < friendsName.size() && !flag){
            if((friendsName.get(index)).equals(friend)){
                flag = true;
                friendsName.remove(index);
            }
            index++;
        }
        return flag;
    }
    
    public int checkFragment(int index){
        return fragments[index];
    }
    
    public int checkAliveTimes(int index){
        return alive[index];
    }
    
    public int checkToolNum(int index){
        return tools[index];
    }
    
    public int checkPhase(int index){
        int phase;
        // phase 0 is primary, phase 1 is middle, and phase 2 is advanced (The appearance of chess will change)
        int num = checkFragment(index);
        if(num<20){
            phase = 0;
        }
        else if(num>=20 && num<50){
            phase = 1;
        }
        else{
            phase = 2;
        }
        return phase;
    }
    
    public void calWinningRate(){
        if(gameTimes != 0){
            winningRate = (float) winnerTimes*100/gameTimes;
        }
        else{
            winningRate = 0;
        }
    }
}


