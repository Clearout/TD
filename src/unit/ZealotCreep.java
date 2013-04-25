package unit;

import world.Map;
import world.World;

import com.example.towerdefence.Game;
import com.example.towerdefence.SoundRepository;

public class ZealotCreep extends Creep {
	public ZealotCreep(Game game, World world, Map map, int life,
			int goldReward, float movespeed) {
		super(game, world, map, life * 5, goldReward * 4, movespeed / 4);
		
		down[0] = "creeps/zealot/zealotDown.png";
		down[1] = "creeps/zealot/zealotDownL.png";
		down[2] = "creeps/zealot/zealotDown.png";
		down[3] = "creeps/zealot/zealotDownR.png";

		up[0] = "creeps/zealot/zealotUp.png";
		up[1] = "creeps/zealot/zealotUpL.png";
		up[2] = "creeps/zealot/zealotUp.png";
		up[3] = "creeps/zealot/zealotUpR.png";

		left[0] = "creeps/zealot/zealotLeft.png";
		left[1] = "creeps/zealot/zealotLeftL.png";
		left[2] = "creeps/zealot/zealotLeft.png";
		left[3] = "creeps/zealot/zealotLeftR.png";

		right[0] = "creeps/zealot/zealotRight.png";
		right[1] = "creeps/zealot/zealotRightL.png";
		right[2] = "creeps/zealot/zealotRight.png";
		right[3] = "creeps/zealot/zealotRightR.png";

		activeImage = game.imageRepository.getCreepImage(down[0]);
		chooseImageSet();
	}
	
	@Override
	public void die() {
		super.die();
		SoundRepository.zealot.play(game.soundVolume);
	}
}
