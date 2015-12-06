package clientpkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Shared.DoubtUser;

public class ActionListnerManager implements ActionListener, PacketNumber {
	ButtonType		type;
	PacketNumber 	packet;
	DoubtUser		playerInfo;
	int[]			card;
	
	public ActionListnerManager(ButtonType type, DoubtUser user) {
		this.type = type;
		
		try {
			this.playerInfo = (DoubtUser)user.clone();
			card = playerInfo.getCard();
			for(int i = 0; i < card.length; i++)
				System.out.println(card[i]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==type.READY){
			comm.sendTo(packet.Ready + playerInfo.getId());
            System.out.println("You clicked the button");
        }
		
        if(e.getSource()==type.DOUBT){
        	comm.sendTo(packet.Doubt + playerInfo.getId());
            System.out.println("You clicked the button");
        }
        
        
	}

}
