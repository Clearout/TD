package unit;

import world.Map;
import world.World;
import com.example.towerdefence.Game;
import com.example.towerdefence.SoundRepository;

public class ImpCreep extends Creep {
	public ImpCreep(Game game, World world, Map map, int life, int goldReward,
			float movespeed) {
		super(game, world, map, life, goldReward, movespeed);
		
		down[0] = "creeps/imp/impDown.png";
		down[1] = "creeps/imp/impDownL.png";
		down[2] = "creeps/imp/impDown.png";
		down[3] = "creeps/imp/impDownR.png";

		up[0] = "creeps/imp/impUp.png";
		up[1] = "creeps/imp/impUpL.png";
		up[2] = "creeps/imp/impUp.png";
		up[3] = "creeps/imp/impUpR.png";

		right[0] = "creeps/imp/impRight.png";
		right[1] = "creeps/imp/impRightL.png";
		right[2] = "creeps/imp/impRight.png";
		right[3] = "creeps/imp/impRightR.png";

		left[0] = "creeps/imp/impLeft.png";
		left[1] = "creeps/imp/impLeftL.png";
		left[2] = "creeps/imp/impLeft.png";
		left[3] = "creeps/imp/impLeftR.png";
		
		activeImage = game.imageRepository.getCreepImage(down[0]);
		chooseImageSet();
	}
	
	@Override
	public void die() {
		super.die();
		SoundRepository.imp.play(game.soundVolume);
	}
}
