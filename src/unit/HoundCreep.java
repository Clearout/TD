package unit;

import world.Map;
import world.World;

import com.example.towerdefence.Game;

public class HoundCreep extends Creep {
	public HoundCreep(Game game, World world, Map map, int life,
			int goldReward, float movespeed) {
		super(game, world, map, (int) (life * 0.7), goldReward, movespeed * 2);

		down[0] = "creeps/hound/houndDownL.png";
		down[1] = "creeps/hound/houndDownR.png";
		down[2] = "creeps/hound/houndDownL.png";
		down[3] = "creeps/hound/houndDownR.png";

		up[0] = "creeps/hound/houndUpL.png";
		up[1] = "creeps/hound/houndUpR.png";
		up[2] = "creeps/hound/houndUpL.png";
		up[3] = "creeps/hound/houndUpR.png";

		left[0] = "creeps/hound/houndLeftL.png";
		left[1] = "creeps/hound/houndLeftR.png";
		left[2] = "creeps/hound/houndLeftL.png";
		left[3] = "creeps/hound/houndLeftR.png";

		right[0] = "creeps/hound/houndRightL.png";
		right[1] = "creeps/hound/houndRightR.png";
		right[2] = "creeps/hound/houndRightL.png";
		right[3] = "creeps/hound/houndRightR.png";

		activeImage = game.imageRepository.getCreepImage(down[0]);
		chooseImageSet();
	}
}
