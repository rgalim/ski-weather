package skiweather.service

class LocationService {

    // The list of locations is static for now and only supports the most popular ski areas in Austria
    fun getLocations(): List<String> {
        return listOf(
            "Sölden",
            "St. Anton am Arlberg",
            "Kitzbühel",
            "Ischgl",
            "Saalbach-Hinterglemm"
//            "Zell am See-Kaprun",
//            "Mayrhofen",
//            "Lech-Zürs",
//            "Obergurgl-Hochgurgl",
//            "Schladming-Dachstein"
        )
    }
}