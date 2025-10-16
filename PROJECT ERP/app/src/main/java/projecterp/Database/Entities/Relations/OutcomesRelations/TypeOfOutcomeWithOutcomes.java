package projecterp.Database.Entities.Relations.OutcomesRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.Outcomes;
import projecterp.Database.Entities.TypeOfOutcome;

import java.util.List;

public class TypeOfOutcomeWithOutcomes {

    @Embedded
    private TypeOfOutcome typeOfOutcome;

    @Relation(
            parentColumn = "typeOfOutcome",
            entityColumn = "typeOfOutcome"
    )
    private List<Outcomes> outcomes;

    public TypeOfOutcome getTypeOfOutcome() {
        return typeOfOutcome;
    }

    public void setTypeOfOutcome(TypeOfOutcome typeOfOutcome) {
        this.typeOfOutcome = typeOfOutcome;
    }

    public List<Outcomes> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<Outcomes> outcomes) {
        this.outcomes = outcomes;
    }
}
