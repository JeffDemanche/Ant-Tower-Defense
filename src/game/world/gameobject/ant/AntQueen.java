package game.world.gameobject.ant;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.ComponentAIBehaviorTree;
import engine.world.gameobject.ComponentCircle;
import engine.world.gameobject.ComponentDynamicSprite;
import engine.world.gameobject.ComponentNavigable;
import engine.world.gameobject.Drawable;
import engine.world.gameobject.behavior.BehaviorBlackboardPut;
import engine.world.gameobject.behavior.BehaviorConditionBlackboardEquals;
import engine.world.gameobject.behavior.BehaviorCustomFunction;
import engine.world.gameobject.behavior.BehaviorCustomFunction.Callback;
import engine.world.gameobject.behavior.BehaviorFollowPath;
import engine.world.gameobject.behavior.BehaviorNode;
import engine.world.gameobject.behavior.BehaviorSelector;
import engine.world.gameobject.behavior.BehaviorSequence;
import engine.world.gameobject.behavior.BehaviorWrapperNot;
import engine.world.gameobject.behavior.Blackboard;
import game.world.gameobject.SugarParticlesEmitter;
import game.world.gameobject.tile.Tile;
import game.world.gameobject.tower.Tower;
import game.world.system.HexCoordinates;
import game.world.system.SystemAnts;
import game.world.system.SystemTowers;
import javafx.scene.input.MouseEvent;

public class AntQueen extends Ant {

	private final double QUEEN_RADIUS = 0.25;
	private final double QUEEN_SPEED = 0.8;

	private ComponentCircle bound;
	private ComponentDynamicSprite sprite;
	private ComponentNavigable navigable;
	private ComponentAIBehaviorTree behaviorTree;

	private Blackboard behaviorBlackboard;
	private SugarParticlesEmitter sugarEmitter;

	private boolean caught;

	public AntQueen(SystemAnts system, int maxHealth, int antId, int reward) {
		super(system, "Queen", antId, maxHealth, reward);

		this.caught = false;

		this.bound = new ComponentCircle(this, new Vec2d(0), QUEEN_RADIUS);
		this.sprite = new ComponentDynamicSprite(this,
				"file:src/img/ant/queen.png", bound, 8, 150);
		this.navigable = new ComponentNavigable(this, QUEEN_SPEED, bound);

		this.createWalkingAnimation();
		this.createDamageAnimation();
		sprite.setAnimation("Walk");

		this.behaviorTree = new ComponentAIBehaviorTree(this, null);

		this.addComponent(behaviorTree);

		this.addComponent(bound);
		this.addComponent(sprite);
		this.addComponent(navigable);
	}

	public AntQueen(Element element, Wave wave, SystemAnts system) {
		this(system, Integer.parseInt(element.getAttribute("id")),
				Integer.parseInt(element.getAttribute("maxHealth")),
				Integer.parseInt(element.getAttribute("reward")));
	}

	private BehaviorNode createBehavior() {
		this.behaviorBlackboard = new Blackboard();

		this.behaviorBlackboard.put("Alive", false);

		// Only checks to make sure alive is true, then runs direction selector
		BehaviorSequence rootSequence = new BehaviorSequence(
				behaviorBlackboard);

		BehaviorConditionBlackboardEquals isAlive = new BehaviorConditionBlackboardEquals(
				behaviorBlackboard, "Alive", true);

		BehaviorSelector directionSelector = new BehaviorSelector(
				behaviorBlackboard);

		BehaviorConditionBlackboardEquals hasSugar = new BehaviorConditionBlackboardEquals(
				behaviorBlackboard, "Has Sugar", true);
		BehaviorSequence findSugarSequence = new BehaviorSequence(
				behaviorBlackboard);
		BehaviorSequence returnToHillSequence = new BehaviorSequence(
				behaviorBlackboard);

		{
			BehaviorWrapperNot doesntHaveSugar = new BehaviorWrapperNot(
					behaviorBlackboard, hasSugar);
			BehaviorFollowPath<HexCoordinates> pathfinderTo = new BehaviorFollowPath<>(
					behaviorBlackboard, pathTo, navigable);
			BehaviorBlackboardPut getSugar = new BehaviorBlackboardPut(
					behaviorBlackboard, "Has Sugar", true);

			findSugarSequence.addChild(doesntHaveSugar);
			findSugarSequence.addChild(pathfinderTo);
			findSugarSequence.addChild(getSugar);
		}
		{
			BehaviorFollowPath<HexCoordinates> pathfinderFrom = new BehaviorFollowPath<HexCoordinates>(
					behaviorBlackboard, pathFrom, navigable);
			BehaviorCustomFunction reachAntHillWithSugar = new BehaviorCustomFunction(
					behaviorBlackboard, new Callback() {
						@Override
						public void callback() {
							anthillDespawn();
						}
					});

			returnToHillSequence.addChild(hasSugar);
			returnToHillSequence.addChild(pathfinderFrom);
			returnToHillSequence.addChild(reachAntHillWithSugar);

		}

		directionSelector.addChild(findSugarSequence);
		directionSelector.addChild(returnToHillSequence);

		rootSequence.addChild(isAlive);
		rootSequence.addChild(directionSelector);

		return rootSequence;
	}

	private int damageTimer = 0;

	@Override
	public boolean damage(int amount, Tower tower) {
		this.damageTimer = ANT_DAMAGE_ANIMATION_TIMER;
		this.sprite.setAnimation("Damage");

		return super.damage(amount, tower);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		super.onTick(nanosSincePreviousTick);

		if (this.caught) {
			this.navigable.setSpeed(0.005);
			return;
		}

		if (behaviorBlackboard.get("Has Sugar").getValue().equals(true)) {
			if (sugarEmitter == null) {
				sugarEmitter = new SugarParticlesEmitter(getSystem(),
						"sugarEmitter", getDrawable().getPosition());
				getSystem().addGameObject(SystemTowers.TOWERS_Z + 3,
						sugarEmitter);
			} else {
				sugarEmitter.adjustPosition(getDrawable().getPosition());
			}

		}

		HexCoordinates currentHex = HexCoordinates
				.fromGameSpace(this.bound.getPosition());
		Tile targetTile = (Tile) ((SystemAnts) this.getSystem()).getLevel()
				.getTileAt(currentHex.getX(), currentHex.getY());
		if (targetTile.getType() == Tile.Type.Honey) {
			this.navigable.setSpeed(QUEEN_SPEED * 0.2);
			// System.out.println("on honey TILE");
		} else {
			this.navigable.setSpeed(QUEEN_SPEED);
		}

		if (damageTimer > 0) {
			damageTimer -= nanosSincePreviousTick / 1000000;
		} else {
			this.sprite.setAnimation("Walk");
		}
	}

	@Override
	public void onSpawn() {

		this.pathTo = getWave().getPaths().getPathTo();
		this.pathFrom = getWave().getPaths().getPathFrom();
		generateSplinePath();

		this.behaviorTree.setRootBehavior(createBehavior());

		super.onSpawn();

		behaviorBlackboard.put("Alive", true);
		behaviorBlackboard.put("Has Sugar", false);
	}

	@Override
	public void kill(Tower tower) {
		super.kill(tower);
		behaviorBlackboard.put("Alive", false);
		if (sugarEmitter != null) {
			sugarEmitter.endParticles();
		}

		this.remove();
	}

	@Override
	public void anthillDespawn() {
		super.anthillDespawn();

		behaviorBlackboard.put("Alive", false);
		if (sugarEmitter != null) {
			sugarEmitter.endParticles();
		}

		this.remove();
	}

	private void createWalkingAnimation() {
		sprite.addPhaseToAnimation("Walk", 0, 0, 16, 16);
		sprite.addPhaseToAnimation("Walk", 16, 0, 16, 16);
		sprite.addPhaseToAnimation("Walk", 32, 0, 16, 16);
		sprite.addPhaseToAnimation("Walk", 48, 0, 16, 16);
	}

	private void createDamageAnimation() {
		sprite.addPhaseToAnimation("Damage", 0, 16, 16, 16);
		sprite.addPhaseToAnimation("Damage", 16, 16, 16, 16);
		sprite.addPhaseToAnimation("Damage", 32, 16, 16, 16);
		sprite.addPhaseToAnimation("Damage", 48, 16, 16, 16);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

	@Override
	public Drawable getBound() {
		return this.bound;
	}

	@Override
	public ComponentAIBehaviorTree getBehaviorTree() {
		return this.behaviorTree;
	}

	@Override
	public int getSugarCap() {
		return 1;
	}

	public boolean isCaught() {
		return caught;
	}

	public void setCaught(boolean caught) {
		this.caught = caught;
	}

	/**
	 * Draw path of ant on mouse hover
	 */
	@Override
	public void onMouseMoved(MouseEvent e) {
		Vec2d clickPos = new Vec2d(e.getX(), e.getY());
		if (getDrawable().insideBB(clickPos)) {
			if (pathSpline != null) {
				if (behaviorBlackboard.get("Has Sugar").getValue()
						.equals(true)) {
					pathSpline.enableShowPathFrom(true);
				} else {
					pathSpline.enableShowPathTo(true);
				}
			}

		} else {
			if (pathSpline != null) {
				pathSpline.enableShowPathTo(false);
				pathSpline.enableShowPathFrom(false);
			}
		}

	}

}
