package nta.engine.exec.eval;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import nta.catalog.Schema;
import nta.catalog.proto.CatalogProtos.DataType;
import nta.datum.Datum;
import nta.engine.json.GsonCreator;
import nta.storage.Tuple;

/**
 * 
 * @author Hyunsik Choi
 *
 */
public abstract class EvalNode {
	@Expose
	protected Type type;
	@Expose
	protected EvalNode leftExpr;
	@Expose
	protected EvalNode rightExpr;
	
	public EvalNode(Type type) {
		this.type = type;
	}
	
	public EvalNode(Type type, EvalNode left, EvalNode right) {
		this(type);
		this.leftExpr = left;
		this.rightExpr = right;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public void setLeftExpr(EvalNode expr) {
		this.leftExpr = expr;
	}
	
	public EvalNode getLeftExpr() {
		return this.leftExpr;
	}
	
	public void setRightExpr(EvalNode expr) {
		this.rightExpr = expr;
	}
	
	public EvalNode getRightExpr() {
		return this.rightExpr;
	}
	
	public abstract DataType getValueType();
	
	public abstract String getName(); 
	
	public String toString() {
		return "("+this.type+"("+leftExpr.toString()+" "+rightExpr.toString()+"))";
	}
	
	public String toJSON() {
	  Gson gson = GsonCreator.getInstance();
    return gson.toJson(this, EvalNode.class);
	}
	
	public abstract Datum eval(Schema schema, Tuple tuple, Datum...args);
	
	public static enum Type {
	  FIELD,
	  FUNCTION,
	  AND,
	  OR,
	  CONST,
	  PLUS,
	  MINUS,
	  MULTIPLY,
	  DIVIDE,
	  EQUAL,
	  NOT_EQUAL,
	  LTH,
	  LEQ,
	  GTH,
	  GEQ,
	  JOIN;
	}
}