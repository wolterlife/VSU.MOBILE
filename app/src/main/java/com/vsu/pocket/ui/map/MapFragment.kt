package com.vsu.pocket.ui.map

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vsu.pocket.MainActivity
import com.vsu.pocket.R
import kotlinx.android.synthetic.main.fragment_schedule.*
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.MapsInitializer.Renderer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback

class MapFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        // Азура, циановый, синий, зелёный, пурпурный, оранжевый, красный, розовый, фиолетовый, жёлтый
        // Точки на карте (depart - корпуса ; hostel - общежития ; food - столовые ; stad - стадионы ; garden - сад)

        // # SETTINGS TYPE OF MAP
        // 1 - стандартная ; other - гибрид;

        //

        //

        val prefs : SharedPreferences?= activity?.getPreferences(Context.MODE_PRIVATE);
        var stypemap = prefs?.getBoolean("data_sattelite",false)
        var sdepartshow = prefs?.getBoolean("data_select_univer",true)
        var shostelshow = prefs?.getBoolean("data_select_hostels",true)
        var sfoodshow = prefs?.getBoolean("data_select_food",true)
        var sstadshow = prefs?.getBoolean("data_select_stadions",true)
        var sgardenshow = prefs?.getBoolean("data_select_garden",true)
        var shospitalshow = prefs?.getBoolean("data_select_hospital",true)



        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled= true



        if (stypemap == false) googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL;
        else googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID;
        // # /

        val depart1 = LatLng(55.17775688973537, 30.225320984601648)  // Main
        val depart2 = LatLng(55.203338105056964, 30.205241695213335) // Пед
        val depart3 = LatLng(55.17703478567047, 30.22425131207067)  // ХГФ
        val depart4 = LatLng(55.198973760261026, 30.23580875369847)  // ФСПИП и ФФКИС

        val hostel1 = LatLng(55.192764848124945, 30.221643365014295)  // Общежитие 1
        val hostel3 = LatLng(55.19315847278591, 30.22416203287052)  // Общежитие 3
        val hostel4 = LatLng(55.174080462995306, 30.222815517813927)  // Общежитие 4
        val hostel5 = LatLng(55.20122570611315, 30.24936403767228)  // Общежитие 5
        val hostel6 = LatLng(55.184617785076505, 30.245776142178627)  // Общежитие 6

        val food1 = LatLng(55.17759879501918, 30.224318095841713)  // Столовая Мск-33
        val food2 = LatLng(55.19921717345365, 30.235664596826467)  // Столовая Чапаева-30
        val food3 = LatLng(55.19267995230562, 30.22049683035245)  // Столовая Мед локус добавить время работы
//        val food4 = LatLng(55.17838967871301, 30.23353349946893)  // Столовая ВГТУ

        val garden = LatLng(55.20135738777659, 30.212225285341127) // Ботанический сад
        val hospital = LatLng(55.19306595192698, 30.22351073932567) // Мед. пункт

        val stad1 = LatLng(55.17539185200047, 30.224814547430658)  // Стадион главного корпуса
        val stad2 = LatLng(55.19859054763544, 30.22924646405134)  // Стадион ФСПИП и ФФКИС
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(depart1,13.1f))


        if (sdepartshow == true) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(depart1)
                    .title("Главный корпус")
                    .snippet("Московский пр-т, 33")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.grad))
            )

            googleMap.addMarker(
                MarkerOptions()
                    .position(depart2)
                    .title("Корпус ПФ")
                    .snippet("ул. Чехова, 11/44")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.grad))
            )

            googleMap.addMarker(
                MarkerOptions()
                    .position(depart3)
                    .title("Корпус ХГФ")
                    .snippet("Московский пр-т, 33А")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.grad))
            )

            googleMap.addMarker(
                MarkerOptions()
                    .position(depart4)
                    .title("Корпус ФСПИП и ФФКИС")
                    .snippet("ул. Чапаева 30")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.grad))
            )
        }

        if (shostelshow == true) {
        googleMap.addMarker(
            MarkerOptions()
                .position(hostel1)
                .title("Общежитие №1")
                .snippet("пр-т. Фрунзе 29")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hostelslarge))
        )
        googleMap.addMarker(
            MarkerOptions()
                .position(hostel3)
                .title("Общежитие №3")
                .snippet("пр-т. Фрунзе 33")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hostelslarge))
        )
        googleMap.addMarker(
            MarkerOptions()
                .position(hostel4)
                .title("Общежитие №4")
                .snippet("ул. Победы 3/1")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hostelslarge))
        )
        googleMap.addMarker(
            MarkerOptions()
                .position(hostel5)
                .title("Общежитие №5")
                .snippet("ул. Лазо 88")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hostelslarge))
        )
        googleMap.addMarker(
            MarkerOptions()
                .position(hostel6)
                .title("Общежитие №6")
                .snippet("ул. Терешковой 18")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hostelslarge))
        )
    }

        if (sfoodshow == true) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(food1)
                    .title("Столовая ВГУ №1")
                    .snippet("Московский пр-т, 33")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.rest))
            )
            googleMap.addMarker(
                MarkerOptions()
                    .position(food2)
                    .title("Столовая ВГУ №2")
                    .snippet("ул. Чапаева 30")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.rest))
            )
            googleMap.addMarker(
                MarkerOptions()
                    .position(food3)
                    .title("Столовая ВГМУ 'Locus Bonus'")
                    .snippet("ул. Терешковой 18")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.rest))
            )
        }

        if (sgardenshow == true) googleMap.addMarker(
                MarkerOptions()
                    .position(garden)
                    .title("Ботанический сад ВГУ")
                    .snippet("ул. Урицкого 6")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.garden))
            )

        if (shospitalshow == true) googleMap.addMarker(
            MarkerOptions()
                .position(hospital)
                .title("Медпункт ВГУ")
                .snippet("пр-т. Фрунзе 33. Вход с торца здания")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hosp))
        )

        if (sstadshow == true) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(stad1)
                    .title("Стадион")
                    .snippet("пр-т Победы 3G")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.stad))
            )
            googleMap.addMarker(
                MarkerOptions()
                    .position(stad2)
                    .title("Стадион")
                    .snippet("пр-т. Генерала Людникова 12")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.stad))
            )
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        // Передача данных о том, что фрагмент отображается в MainActivity
        val prefs : SharedPreferences?= activity?.getPreferences(Context.MODE_PRIVATE);
        prefs?.edit()?.putBoolean("s_map" , true)?.apply();
        setHasOptionsMenu(true);
    }
}