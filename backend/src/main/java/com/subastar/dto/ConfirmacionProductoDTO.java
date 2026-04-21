package com.subastar.dto;
import com.subastar.model.enums.DecisionProducto;
import jakarta.validation.constraints.NotNull;
public class ConfirmacionProductoDTO {
    @NotNull private DecisionProducto decision;
    public DecisionProducto getDecision() { return decision; }
    public void setDecision(DecisionProducto decision) { this.decision = decision; }
}
