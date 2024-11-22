package project.Rent.a.car.util;

public class Utility {

    public static Double calcPrice(double carPrice, double rentDays, boolean isWeekend, boolean hasAccidents){
        double total = carPrice * rentDays;

        if (isWeekend) total=total*1.10;

        if (hasAccidents) total=total+200;

        return total;
    }



    public static boolean isCarInTheCity(int cityId){
        return cityId <= 4 && cityId >= 1;
    }

}
