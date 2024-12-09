package com.liontail.arfind.api
import com.liontail.arfind.dispositivos.CambiarPlanDto
import com.liontail.arfind.dispositivos.CambiarPlanResonse
import com.liontail.arfind.dispositivos.ChangeApodoDto
import com.liontail.arfind.dispositivos.ChangeApodoResponse
import com.liontail.arfind.dispositivos.DarseBajaDto
import com.liontail.arfind.dispositivos.DarseBajaResponse
import com.liontail.arfind.dispositivos.DispositivoDto
import com.liontail.arfind.dispositivos.EliminarInvitadoDto
import com.liontail.arfind.dispositivos.EliminarInvitadoResponse
import com.liontail.arfind.dispositivos.GeneratedCodigoDto
import com.liontail.arfind.dispositivos.GeneratedCodigoResponse
import com.liontail.arfind.dispositivos.SubmitCodeDto
import com.liontail.arfind.dispositivos.SubmitCodeResponse
import com.liontail.arfind.dispositivos.UbicacionDto
import com.liontail.arfind.firebase.dto.UsuarioDto
import com.liontail.arfind.mercadopago.MPCart
import com.liontail.arfind.mercadopago.MPCart2
import com.liontail.arfind.mercadopago.MPResponse
import com.liontail.arfind.notificaciones.NotificacionDto
import com.liontail.arfind.planes.PlanesDto
import com.liontail.arfind.productos.ProductoDto
import com.liontail.arfind.register.MailResponse
import com.liontail.arfind.register.PinUsuario
import com.liontail.arfind.register.PinVerify
import com.liontail.arfind.register.ResponseUserInfo
import com.liontail.arfind.register.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
interface ApiService {

        @GET("/productos/productos")
        fun getProductos(): Call<List<ProductoDto>>

        @GET("/planes/getPlanesAndroid")
        fun getPlanes(): Call<List<PlanesDto>>

        @GET("/dispositivos/getDispositivosByUsuario")
        fun getDispositivosByUsuario(): Call<List<DispositivoDto>>

        @GET("/dispositivos/getDispositivosInvitados")
        fun getDispositivosInvitados(): Call<List<DispositivoDto>>

        @POST("mercadoPago/crearOrdenDinamica")
        fun createOrdenMercadoPago(@Body mpCart: MPCart): Call<String>
        @POST("mercadoPago/crearOrdenDinamicaWeb")
        fun createOrdenMercadoPagoWeb(@Body mpCart: MPCart): Call<MPResponse>
        @POST("mercadoPago/crearOrdenDinamicaAndroid")
        fun createOrdenMercadoPagoAndroid(@Body mpCart: MPCart): Call<MPResponse>

        @POST("mercadoPago/crearOrdenDinamicaAndroid2")
        fun createOrdenMercadoPagoAndroid2(@Body mpCart: MPCart2): Call<MPResponse>

        @GET("/auth/verifyPIN")
        fun isPinOk(
                @Query("email") email: String,
                @Query("pin") pin: String,
        ): Call<PinVerify>

        @POST("/auth/sendCodeByMail")
        fun sendCodeByMail(@Body pinUsuario : PinUsuario): Call<MailResponse>

        @POST("/auth/registerUser")
        fun registerUser(@Body  user : UserInfo): Call<ResponseUserInfo>

        @PUT("/dispositivos/generateCodigoInvitado")
        fun generateCodigoInvitado(@Body generatedCodigo : GeneratedCodigoDto):Call<GeneratedCodigoResponse>

        @PUT("/dispositivos/updateApodoDispositivo")
        fun updateApodoDispositivo(@Body changeApodoDto : ChangeApodoDto):Call<ChangeApodoResponse>

        @GET("/dispositivos/getUserInfo")
        fun getUserById(): Call<UsuarioDto>

        @POST("/dispositivos/submitCodigoInvitado")
        fun agregarDispositivo(@Body submitCodeDto: SubmitCodeDto): Call<SubmitCodeResponse>

        @GET("/dispositivos/getDispositivoUbicacionByDeviceId/{id}")
        fun getUbicacion(@Path("id") deviceId: String?): Call<UbicacionDto?>?

        @POST("/dispositivos/darseDeBaja")
        fun darseDeBaja(@Body deviceId: DarseBajaDto?): Call<DarseBajaResponse?>?

        @POST("/dispositivos/eliminarInvitados")
        fun eliminarInvitados(@Body eliminarInvitadoDto: EliminarInvitadoDto?): Call<EliminarInvitadoResponse?>?

        @POST("/dispositivos/cambiarPlan")
        fun cambiarPlan(@Body cambiarPlanDto: CambiarPlanDto): Call<CambiarPlanResonse?>?

        @GET("/notificaciones/misNotificaciones")
        fun misNotificaciones(): Call<List<NotificacionDto>?>?

}