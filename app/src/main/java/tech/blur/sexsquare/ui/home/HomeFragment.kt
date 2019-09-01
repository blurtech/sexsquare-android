package tech.blur.sexsquare.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.blur.sexsquare.R
import tech.blur.sexsquare.databinding.FragmentHomeBinding

class HomeFragment : Fragment(),
    OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var googleMap: GoogleMap

    private lateinit var googleApiClient: GoogleApiClient

    private var mBottomSheetBehavior: BottomSheetBehavior<View?>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )


        val view = binding.root

        view.mapView.onCreate(savedInstanceState)

        view.mapView.onResume() // needed to get the map to display immediately

        googleApiClient = GoogleApiClient.Builder(context!!)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        googleApiClient.connect()


        view.mapView.getMapAsync(this)

        binding.executePendingBindings()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeViewModel.text.observe(this, Observer {

        })

        configureBackdrop()
    }

    private fun configureBackdrop() {
        // Get the fragment reference
        val fragment = childFragmentManager.findFragmentById(R.id.fragment_statistic_Home)

        fragment?.let {
            // Get the BottomSheetBehavior from the fragment view
            BottomSheetBehavior.from(it.view)?.let { bsb ->
                // Set the initial state of the BottomSheetBehavior to HIDDEN
                bsb.state = BottomSheetBehavior.PEEK_HEIGHT_AUTO

                // Set the reference into class attribute (will be used latter)
                mBottomSheetBehavior = bsb
            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!

        val permList =
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET)

        ActivityCompat.requestPermissions(activity!!, permList, 1)

        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "No Location Permissions", Toast.LENGTH_LONG).show()
        } else {
            googleMap.isMyLocationEnabled = true
//            googleMap.setOnMyLocationButtonClickListener(this)
            //googleMap.setOnMyLocationClickListener(this)
            //googleMap.setOnMapClickListener(this)
            //googleMap.setOnMapLongClickListener(this)
//            googleMap.setOnMarkerClickListener(this)
            //googleMap.setOnMarkerDragListener(this)
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        //googleMap.clear()
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        val location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
        if (location != null) {
            moveMap(location.latitude, location.longitude)
        }
    }

    private fun moveMap(latitude: Double, longitude: Double) {
        /**
         * Creating the latlng object to store lat, long coordinates
         * adding marker to map
         * move the camera with animation
         */
        val latLng = LatLng(latitude, longitude)

        val cameraPosition = CameraPosition.Builder().target(latLng).zoom(15f).build()

        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        googleMap.uiSettings.isZoomControlsEnabled = true
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onConnected(p0: Bundle?) {
        getCurrentLocation()
    }

    override fun onConnectionSuspended(p0: Int) {

    }

}