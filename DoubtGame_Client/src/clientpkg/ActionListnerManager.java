package clientpkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListnerManager implements ActionListener {
	int buttonNum;
	
	public ActionListnerManager(int buttonNum) {
		this.buttonNum = buttonNum;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==rdyBtn){
			comm.sendTo(packet.Ready + playerInfo.getId());
			System.out.println(playerInfo.getId());
            System.out.println("You clicked the button");
        }
		
        if(e.getSource()==doubtBtn){
        	comm.sendTo(packet.Doubt + playerInfo.getId());
            System.out.println("You clicked the button");
        }
        
        if(e.getSource()==nextBtn){
        	buttonCnt++;	
			currPlayersDeck = repaintDeck(currPlayersDeck, "currentPlayer", buttonCnt);
			
			System.out.println(buttonCnt);
            if(buttonCnt >= 0 && buttonCnt < 2){
            	nextBtn.setEnabled(true);
            	prevBtn.setEnabled(true);
            } else
            	nextBtn.setEnabled(false);		
        }
	}

}
