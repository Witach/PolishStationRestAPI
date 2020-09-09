package pl.polishstation.polishstationbackend.domain.fuel.fuelprice;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PrecisionValidator implements ConstraintValidator<Precision, Double> {
   public void initialize(Precision constraint) {
   }

   public boolean isValid(Double obj, ConstraintValidatorContext context) {
      var moneyLabel = Double.toString(obj);
      return !moneyLabel.contains(".") || checkForPrecision(moneyLabel);
   }

   private boolean checkForPrecision(String moneyLabel) {
      return moneyLabel.split("\\.")[1].length() == 2;
   }
}
