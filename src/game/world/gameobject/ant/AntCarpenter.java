package game.world.gameobject.ant;

import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.ComponentAIBehaviorTree;
import engine.world.gameobject.ComponentCircle;
import engine.world.gameobject.ComponentDynamicSprite;
import engine.world.gameobject.ComponentNavigable;
import engine.world.gameobject.Drawable;
import engine.world.gameobject.behavior.BehaviorAIPathfind;
import engine.world.gameobject.behavior.BehaviorBlackboardPut;
import engine.world.gameobject.behavior.BehaviorConditionBlackboardEquals;
import engine.world.gameobject.behavior.BehaviorCustomFunction;
import engine.world.gameobject.behavior.BehaviorFollowPath;
import engine.world.gameobject.behavior.BehaviorCustomFunction.Callback;
import engine.world.gameobject.behavior.BehaviorNode;
import engine.world.gameobject.behavior.BehaviorSelector;
import engine.world.gameobject.behavior.BehaviorSequence;
import engine.world.gameobject.behavior.BehaviorWrapperNot;
import engine.world.gameobject.behavior.Blackboard;
import game.world.system.HexCoordinates;
import game.world.system.SystemAnts;

public class AntCarpenter extends Ant {

	private final double CARPENTER_RADIUS = 0.2;
	private final double CARPENTER_SPEED = 1.5;

	private static final int CARPENTER_MAX_HEALTH = 20;

	private SystemAnts ants;

	private ComponentCircle bound;
	private ComponentDynamicSprite sprite;
	private ComponentNavigable navigable;
	private ComponentAIBehaviorTree behaviorTree;

	private Stack<HexCoordinates> pathTo;
	private Stack<HexCoordinates> pathFrom;

	private Blackboard behaviorBlackboard;

	public AntCarpenter(SystemAnts system, int antId) {
		super(system, "Carpenter", antId, CARPENTER_MAX_HEALTH);

		this.ants = system;

		this.bound = new ComponentCircle(this, new Vec2d(0), CARPENTER_RADIUS);
		this.sprite = new ComponentDynamicSprite(this,
				"file:src/img/ant/carpenter.png", bound, 8, 150);
		this.navigable = new ComponentNavigable(this, CARPENTER_SPEED, bound);

		this.createWalkingAnimation();
		this.createDamageAnimation();
		sprite.setAnimation("Walk");

		this.behaviorTree = new ComponentAIBehaviorTree(this, null);
		
		this.addComponent(behaviorTree);
		
		this.addComponent(bound);
		this.addComponent(sprite);
		this.addComponent(navigable);
	}

	public AntCarpenter(Element element, Wave wave, SystemAnts system) {
		this(system, Integer.parseInt(element.getAttribute("id")));
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
	public void damage(int amount) {
		super.damage(amount);

		this.damageTimer = ANT_DAMAGE_ANIMATION_TIMER;
		this.sprite.setAnimation("Damage");
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		super.onTick(nanosSincePreviousTick);

		if (damageTimer > 0) {
			damageTimer -= nanosSincePreviousTick / 1000000;
		} else {
			this.sprite.setAnimation("Walk");
		}
	}

	@Override
	public void onSpawn() {
		super.onSpawn();

		this.pathTo = getWave().getPaths().getPathTo();
		this.pathFrom = getWave().getPaths().getPathFrom();
		
		this.behaviorTree.setRootBehavior(createBehavior());

		behaviorBlackboard.put("Alive", true);
		behaviorBlackboard.put("Has Sugar", false);
	}

	@Override
	public void kill() {
		super.kill();
		behaviorBlackboard.put("Alive", false);
		this.remove();
	}

	@Override
	public void anthillDespawn() {
		super.anthillDespawn();

		behaviorBlackboard.put("Alive", false);
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

}
