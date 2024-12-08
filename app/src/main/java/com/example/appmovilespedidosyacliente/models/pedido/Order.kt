import com.example.appmovilespedidosyacliente.models.pedido.OrderDetail


data class Order (
    val id: Int,
    val user_id: Int,
    val restaurant_id: Int,
    val total: String,
    val latitude: String,
    val longitude: String,
    val address: String,
    val driverID: Any? = null,
    val status: String,
    val createdAt: String,
    val deliveryProof: String,
    val orderDetails: List<OrderDetail>
)
typealias Orders = ArrayList<Order>