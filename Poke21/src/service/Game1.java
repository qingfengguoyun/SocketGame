package service;

import entity.Player;

import java.util.*;

public class Game1 implements Runnable {

    Player p1=new Player(1,"p1");
    Player p2=new Player(2,"p2");

    Map<Integer,Player> playerMap=new HashMap<>();

    Scanner scanner=new Scanner(System.in);

    public Game1() {
        this.playerMap.put(1,p1);
        this.playerMap.put(2,p2);
    }

    public List<Player> checkChampin(){
        Integer sum=-1;
        List<Player>champins=new LinkedList<>();
        for(Player player:playerMap.values()){
            if(player.getSum()>sum){
                champins.clear();
                champins.add(player);
                sum=player.getSum();
            }
            else if(player.getSum()==sum){
                champins.add(player);
            }
        }
        return champins;
    }

    @Override
    public void run() {

        System.out.println("游戏开始");
        while(true){
            String in = scanner.nextLine();
            if("end".equals(in)){

            }else{
                try {
                    String[] ops = in.split("-");
                    Integer id=Integer.valueOf(ops[0]);
                    Integer cardValue=Integer.valueOf(ops[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
