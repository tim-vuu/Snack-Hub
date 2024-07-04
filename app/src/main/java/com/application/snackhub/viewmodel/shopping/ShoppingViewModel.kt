package com.application.snackhub.viewmodel.shopping

import android.util.Log
import androidx.lifecycle.MusandwichLiveData
import androidx.lifecycle.ViewModel
import com.application.snackhub.firebaseDatabase.FirebaseDb
import com.application.snackhub.model.*
import com.application.snackhub.resource.Resource
import com.application.snackhub.util.Constants.Companion.PIZZA_CATEGORY
import com.application.snackhub.util.Constants.Companion.BURGER_CATEGORY
import com.application.snackhub.util.Constants.Companion.TEA_CATEGORY
import com.application.snackhub.util.Constants.Companion.SNACK_CATEGORY
import com.application.snackhub.util.Constants.Companion.SANDWICHS_CATEGORY
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

private const val TAG = "ShoppingViewModel"

class ShoppingViewModel(
    private val firebaseDatabase: FirebaseDb
) : ViewModel() {

    val clothes = MusandwichLiveData<List<Product>>()
    val emptyClothes = MusandwichLiveData<Boolean>()
    val bestDeals = MusandwichLiveData<List<Product>>()
    val emptyBestDeals = MusandwichLiveData<Boolean>()

    val home = MusandwichLiveData<Resource<List<Product>>>()

    val burgers = MusandwichLiveData<Resource<List<Product>>>()
    val mostRequestedBurgers = MusandwichLiveData<Resource<List<Product>>>()

    val sandwichs = MusandwichLiveData<Resource<List<Product>>>()
    val mostRequestedSandwichs = MusandwichLiveData<Resource<List<Product>>>()

    val pizza = MusandwichLiveData<Resource<List<Product>>>()
    val mostRequestedAccessories = MusandwichLiveData<Resource<List<Product>>>()

    val snack = MusandwichLiveData<Resource<List<Product>>>()
    val mostRequestedSnack = MusandwichLiveData<Resource<List<Product>>>()

    val mostRequestedTea = MusandwichLiveData<Resource<List<Product>>>()
    val tea = MusandwichLiveData<Resource<List<Product>>>()
    val addToCart = MusandwichLiveData<Resource<Boolean>>()

    val addAddress = MusandwichLiveData<Resource<Address>>()
    val updateAddress = MusandwichLiveData<Resource<Address>>()
    val deleteAddress = MusandwichLiveData<Resource<Address>>()

    val profile = MusandwichLiveData<Resource<User>>()

    val uploadProfileImage = MusandwichLiveData<Resource<String>>()
    val updateUserInformation = MusandwichLiveData<Resource<User>>()

    val userOrders = MusandwichLiveData<Resource<List<Order>>>()

    val passwordReset = MusandwichLiveData<Resource<String>>()

    val orderAddress = MusandwichLiveData<Resource<Address>>()
    val orderProducts = MusandwichLiveData<Resource<List<CartProduct>>>()

    val categories = MusandwichLiveData<Resource<List<Category>>>()


    val search = MusandwichLiveData<Resource<List<Product>>>()

    private var homePage: Long = 10
    private var clothesPaging: Long = 5
    private var bestDealsPaging: Long = 5

    private var teaPaging: Long = 4
    private var mostOrderTeaPaging: Long = 5

    private var mostRequestedBurgersPage: Long = 3
    private var burgersPage: Long = 4

    private var mostRequestedSandwichPage: Long = 3
    private var sandwichPage: Long = 4

    private var mostRequestedPizzaPage: Long = 3
    private var pizzaPage: Long = 4

    private var mostRequestedSnackPage: Long = 3
    private var snackPage: Long = 4


    init {
        getClothesProducts()
        getBestDealsProduct()
        getBreadVN()
    }

    private var snackProducts: List<Product>? = null
    fun getSnack(size: Int = 0) {
        if (snackProducts != null && size == 0) {
            snack.postValue(Resource.Success(snackProducts))
            return
        }
        snack.postValue(Resource.Loading())
        shouldPaging(SNACK_CATEGORY, size) { shouldPaging ->
            if (shouldPaging) {
                sandwichs.postValue(Resource.Loading())
                firebaseDatabase.getProductsByCategory(SNACK_CATEGORY, snackPage)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val documents = it.result
                            if (!documents!!.isEmpty) {
                                val productsList = documents.toObjects(Product::class.java)
                                snackProducts = productsList
                                snack.postValue(Resource.Success(productsList))
                                snackPage += 4

                            }
                        } else
                            snack.postValue(Resource.Error(it.exception.toString()))
                    }
            } else
                snack.postValue(Resource.Error("Cannot paging"))

        }
    }

    private var mostRequestedSnackProducts: List<Product>? = null
    fun getMostRequestedSnack(size: Int = 0) {
        if (mostRequestedSnackProducts != null && size == 0) {
            mostRequestedSnack.postValue(Resource.Success(mostRequestedSnackProducts))
            return
        }
        mostRequestedSnack.postValue(Resource.Loading())
        shouldPaging(SNACK_CATEGORY, size) { shouldPaging ->
            if (shouldPaging) {
                mostRequestedSnack.postValue(Resource.Loading())
                firebaseDatabase.getProductsByCategory(
                    SNACK_CATEGORY,
                    mostRequestedSnackPage
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val documents = it.result
                            if (!documents!!.isEmpty) {
                                val productsList = documents.toObjects(Product::class.java)
                                mostRequestedSnackProducts = productsList
                                mostRequestedSnack.postValue(Resource.Success(productsList))
                                mostRequestedSnackPage += 4

                            }
                        } else
                            mostRequestedSnack.postValue(Resource.Error(it.exception.toString()))
                    }
            } else
                mostRequestedSnack.postValue(Resource.Error("Cannot paging"))
        }
    }

    private var accessoriesProducts: List<Product>? = null
    fun getAccessories(size: Int = 0) {
        if (accessoriesProducts != null && size == 0) {
            pizza.postValue(Resource.Success(accessoriesProducts))
            return
        }
        pizza.postValue(Resource.Loading())
        shouldPaging(PIZZA_CATEGORY, size) { shouldPaging ->
            if (shouldPaging) {
                Log.d("test", "paging")
                firebaseDatabase.getProductsByCategory(PIZZA_CATEGORY, pizzaPage)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val documents = it.result
                            if (!documents!!.isEmpty) {
                                val productsList = documents.toObjects(Product::class.java)
                                pizza.postValue(Resource.Success(productsList))
                                accessoriesProducts = productsList
                                pizzaPage += 4

                            }
                        } else
                            pizza.postValue(Resource.Error(it.exception.toString()))
                    }
            } else {
                pizza.postValue(Resource.Error("Cannot page"))
            }
        }
    }

    private var mostRequestedAccessoriesProducts: List<Product>? = null
    fun getMostRequestedAccessories(size: Int = 0) {
        if (mostRequestedAccessoriesProducts != null && size == 0) {
            mostRequestedAccessories.postValue(Resource.Success(mostRequestedAccessoriesProducts))
            return
        }
        mostRequestedAccessories.postValue(Resource.Loading())
        shouldPaging(PIZZA_CATEGORY, size) { shouldPaging ->
            if (shouldPaging) {
                burgers.postValue(Resource.Loading())
                firebaseDatabase.getProductsByCategory(
                    PIZZA_CATEGORY,
                    mostRequestedPizzaPage
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val documents = it.result
                            if (!documents!!.isEmpty) {
                                val productsList = documents.toObjects(Product::class.java)
                                mostRequestedAccessories.postValue(Resource.Success(productsList))
                                mostRequestedAccessoriesProducts = productsList
                                mostRequestedPizzaPage += 4

                            }
                        } else
                            mostRequestedAccessories.postValue(Resource.Error(it.exception.toString()))
                    }
            } else
                mostRequestedAccessories.postValue(Resource.Error("Cannot paging"))
        }
    }

    private var burgersProducts: List<Product>? = null
    fun getBurgers(size: Int = 0) {
        if (burgersProducts != null && size == 0) {
            burgers.postValue(Resource.Success(burgersProducts))
            return
        }
        burgers.postValue(Resource.Loading())
        shouldPaging(TEA_CATEGORY, size) { shouldPaging ->
            if (shouldPaging) {

                burgers.postValue(Resource.Loading())
                firebaseDatabase.getProductsByCategory(BURGER_CATEGORY, burgersPage)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val documents = it.result
                            if (!documents!!.isEmpty) {
                                val productsList = documents.toObjects(Product::class.java)
                                burgersProducts = productsList
                                burgers.postValue(Resource.Success(productsList))
                                burgersPage += 4

                            }
                        } else
                            burgers.postValue(Resource.Error(it.exception.toString()))
                    }
            } else
                burgers.postValue(Resource.Error("Cannot paging"))
        }
    }

    private var mostRequestedBurgersProducts: List<Product>? = null
    fun getMostRequestedBurgers(size: Int = 0) {
        if (mostRequestedBurgersProducts != null && size == 0) {
            mostRequestedBurgers.postValue(Resource.Success(burgersProducts))
            return
        }
        mostRequestedBurgers.postValue(Resource.Loading())
        shouldPaging(TEA_CATEGORY, size) { shouldPaging ->
            if (shouldPaging) {
                burgers.postValue(Resource.Loading())
                firebaseDatabase.getProductsByCategory(BURGER_CATEGORY, mostRequestedBurgersPage)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val documents = it.result
                            if (!documents!!.isEmpty) {
                                val productsList = documents.toObjects(Product::class.java)
                                mostRequestedBurgersProducts = productsList
                                mostRequestedBurgers.postValue(Resource.Success(productsList))
                                mostRequestedBurgersPage += 4

                            }
                        } else
                            mostRequestedBurgers.postValue(Resource.Error(it.exception.toString()))
                    }
            } else
                burgers.postValue(Resource.Error("Cannot paging"))
        }
    }

    private var sandwichsProducts: List<Product>? = null
    fun getSandwichs(size: Int = 0) {
        if (sandwichsProducts != null && size == 0) {
            sandwichs.postValue(Resource.Success(sandwichsProducts))
            return
        }
        sandwichs.postValue(Resource.Loading())
        shouldPaging(SANDWICHS_CATEGORY, size) { shouldPaging ->
            if (shouldPaging) {
                sandwichs.postValue(Resource.Loading())
                firebaseDatabase.getProductsByCategory(SANDWICHS_CATEGORY, sandwichPage)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val documents = it.result
                            if (!documents!!.isEmpty) {
                                val productsList = documents.toObjects(Product::class.java)
                                sandwichsProducts = productsList
                                sandwichs.postValue(Resource.Success(productsList))
                                sandwichPage += 4

                            }
                        } else
                            sandwichs.postValue(Resource.Error(it.exception.toString()))
                    }
            } else
                home.postValue(Resource.Error("Cannot paging"))
        }
    }

    private var mostRequestedSandwichsProducts: List<Product>? = null
    fun getMostRequestedSandwichs(size: Int = 0) {
        if (mostRequestedSandwichsProducts != null && size == 0) {
            sandwichs.postValue(Resource.Success(mostRequestedSandwichsProducts))
            return
        }
        mostRequestedSandwichs.postValue(Resource.Loading())
        shouldPaging(SANDWICHS_CATEGORY, size) { shouldPaging ->
            if (shouldPaging) {
                mostRequestedSandwichs.postValue(Resource.Loading())
                firebaseDatabase.getProductsByCategory(SANDWICHS_CATEGORY, mostRequestedSandwichPage)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val documents = it.result
                            if (!documents!!.isEmpty) {
                                val productsList = documents.toObjects(Product::class.java)
                                mostRequestedSandwichsProducts = productsList
                                mostRequestedSandwichs.postValue(Resource.Success(productsList))
                                mostRequestedSandwichPage += 3

                            }
                        } else
                            mostRequestedSandwichs.postValue(Resource.Error(it.exception.toString()))
                    }
            } else
                mostRequestedSandwichs.postValue(Resource.Error("Cannot paging"))
        }
    }


    fun getClothesProducts() =
        firebaseDatabase.getClothesProducts(clothesPaging).addOnCompleteListener {
            if (it.isSuccessful) {
                val documents = it.result
                if (!documents!!.isEmpty) {
                    val productsList = documents.toObjects(Product::class.java)
                    clothes.postValue(productsList)
                    clothesPaging += 5
                } else
                    emptyClothes.postValue(true)

            } else
                Log.e(TAG, it.exception.toString())

        }

    fun getBestDealsProduct() =
        firebaseDatabase.getBestDealsProducts(bestDealsPaging).addOnCompleteListener {
            if (it.isSuccessful) {
                val documents = it.result
                if (!documents!!.isEmpty) {
                    val productsList = documents.toObjects(Product::class.java)
                    bestDeals.postValue(productsList)
                    bestDealsPaging += 5
                } else
                    emptyBestDeals.postValue(true)

            } else
                Log.e(TAG, it.exception.toString())
        }

    fun getBreadVN(size: Int = 0) {
        home.postValue(Resource.Loading())
        shouldPagingHome(size)
        { shouldPaging ->
            if (shouldPaging) {
                home.postValue(Resource.Loading())
                firebaseDatabase.getBreadVNs(homePage)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val documents = it.result
                            if (!documents!!.isEmpty) {
                                val productsList = documents.toObjects(Product::class.java)
                                home.postValue(Resource.Success(productsList))
                                homePage += 4

                            }
                        } else
                            home.postValue(Resource.Error(it.exception.toString()))
                    }
            } else
                home.postValue(Resource.Error("Cannot paging"))
        }
    }

    private var mostRequestedTeaProducts: List<Product>? = null
    fun getMostRequestedTeas(size: Int = 0) {
        if (mostRequestedTeaProducts != null && size == 0) {
            mostRequestedTea.postValue(Resource.Success(mostRequestedTeaProducts))
            return
        }

        mostRequestedTea.postValue(Resource.Loading())
        shouldPaging(TEA_CATEGORY, size) { shouldPaging ->
            if (shouldPaging) {
                mostRequestedTea.postValue(Resource.Loading())
                firebaseDatabase.getMostOrderedTea(mostOrderTeaPaging)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val documents = it.result
                            if (!documents!!.isEmpty) {
                                val productsList = documents.toObjects(Product::class.java)
                                mostRequestedTeaProducts = productsList
                                mostRequestedTea.postValue(Resource.Success(productsList))
                                mostOrderTeaPaging += 5

                            }
                        } else
                            mostRequestedTea.postValue(Resource.Error(it.exception.toString()))
                    }


            } else
                mostRequestedTea.postValue(Resource.Error("Cannot paging"))
        }
    }

    private var dTeaProducts: List<Product>? = null
    fun getTeaProduct(size: Int = 0) {
        if (dTeaProducts != null && size == 0) {
            tea.postValue(Resource.Success(dTeaProducts))
            return
        }
        shouldPaging(TEA_CATEGORY, size) { shouldPaging ->
            if (shouldPaging) {
                tea.postValue(Resource.Loading())
                firebaseDatabase.getTeas(teaPaging).addOnCompleteListener {
                    if (it.isSuccessful) {

                        val documents = it.result
                        if (!documents!!.isEmpty) {
                            val productsList = documents.toObjects(Product::class.java)
                            dTeaProducts = productsList
                            tea.postValue(Resource.Success(productsList))
                            teaPaging += 10
                        }

                    } else
                        tea.postValue(Resource.Error(it.exception.toString()))
                }
            } else
                tea.postValue(Resource.Error("Cannot paging"))
        }
    }

    /*
    * TODO : Move these functions to firebaseDatabase class
     */

    private fun shouldPaging(category: String, listSize: Int, onSuccess: (Boolean) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection("categories")
            .whereEqualTo("name", category).get().addOnSuccessListener {
                val tempCategory = it.toObjects(Category::class.java)
                val products = tempCategory[0].products
                Log.d("test", " $category : prodcuts ${tempCategory[0].products}, size $listSize")
                if (listSize == products)
                    onSuccess(false).also { Log.d(TAG, "$category Paging:false") }
                else
                    onSuccess(true).also { Log.d(TAG, "$category Paging:true") }
            }
    }

    private fun shouldPagingHome(listSize: Int, onSuccess: (Boolean) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection("categories").get().addOnSuccessListener {
                var productsCount = 0
                it.toObjects(Category::class.java).forEach { category ->
                    productsCount += category.products!!.toInt()
                }

                if (listSize == productsCount)
                    onSuccess(false)
                else
                    onSuccess(true)

            }
    }


    private fun checkIfProductAlreadyAdded(
        product: CartProduct,
        onSuccess: (Boolean, String) -> Unit
    ) {
        addToCart.postValue(Resource.Loading())
        firebaseDatabase.getProductInCart(product).addOnCompleteListener {
            if (it.isSuccessful) {
                val documents = it.result!!.documents
                if (documents.isNotEmpty())
                    onSuccess(true, documents[0].id) // true ---> product is already in cart
                else
                    onSuccess(false, "") // false ---> product is not in cart
            } else
                addToCart.postValue(Resource.Error(it.exception.toString()))

        }
    }


    fun addProductToCart(product: CartProduct) =
        checkIfProductAlreadyAdded(product) { isAdded, id ->
            if (isAdded) {
                firebaseDatabase.increaseProductQuantity(id).addOnCompleteListener {
                    if (it.isSuccessful)
                        addToCart.postValue(Resource.Success(true))
                    else
                        addToCart.postValue(Resource.Error(it.exception!!.message))

                }
            } else {
                firebaseDatabase.addProductToCart(product).addOnCompleteListener {
                    if (it.isSuccessful)
                        addToCart.postValue(Resource.Success(true))
                    else
                        addToCart.postValue(Resource.Error(it.exception!!.message))
                }
            }
        }


    fun saveAddress(address: Address) {
        addAddress.postValue(Resource.Loading())
        firebaseDatabase.saveNewAddress(address)?.addOnCompleteListener {
            if (it.isSuccessful)
                addAddress.postValue(Resource.Success(address))
            else
                addAddress.postValue(Resource.Error(it.exception.toString()))
        }
    }

    fun updateAddress(oldAddress: Address, newAddress: Address) {
        updateAddress.postValue(Resource.Loading())
        firebaseDatabase.findAddress(oldAddress).addOnCompleteListener { addressResponse ->
            if (addressResponse.isSuccessful) {
                val documentUid = addressResponse.result!!.documents[0].id
                firebaseDatabase.updateAddress(documentUid, newAddress)?.addOnCompleteListener {
                    if (it.isSuccessful)
                        updateAddress.postValue(Resource.Success(newAddress))
                    else
                        updateAddress.postValue(Resource.Error(it.exception.toString()))

                }

            } else
                updateAddress.postValue(Resource.Error(addressResponse.exception.toString()))

        }
    }

    fun deleteAddress(address: Address) {
        deleteAddress.postValue(Resource.Loading())
        firebaseDatabase.findAddress(address).addOnCompleteListener { addressResponse ->
            if (addressResponse.isSuccessful) {
                val documentUid = addressResponse.result!!.documents[0].id
                firebaseDatabase.deleteAddress(documentUid, address)?.addOnCompleteListener {
                    if (it.isSuccessful)
                        deleteAddress.postValue(Resource.Success(address))
                    else
                        deleteAddress.postValue(Resource.Error(it.exception.toString()))

                }

            } else
                deleteAddress.postValue(Resource.Error(addressResponse.exception.toString()))

        }
    }

    private val user: User? = null
    fun getUser() {
        if (user != null) {
            profile.postValue(Resource.Success(user))
            return
        }

        profile.postValue(Resource.Loading())
        firebaseDatabase.getUser().addSnapshotListener { value, error ->
            if (error != null)
                profile.postValue(Resource.Error(error.message))
            else
                profile.postValue(Resource.Success(value?.toObject(User::class.java)))

        }
    }

    fun uploadProfileImage(image: ByteArray) {
        uploadProfileImage.postValue(Resource.Loading())
        val name = UUID.nameUUIDFromBytes(image).toString()
        firebaseDatabase.uploadUserProfileImage(image, name).addOnCompleteListener {
            if (it.isSuccessful)
                uploadProfileImage.postValue(Resource.Success(name))
            else
                uploadProfileImage.postValue(Resource.Error(it.exception.toString()))
        }
    }

    fun updateInformation(firstName: String, lastName: String, email: String, imageName: String) {
        updateUserInformation.postValue(Resource.Loading())

        firebaseDatabase.getImageUrl(firstName, lastName, email, imageName) { user, exception ->

            if (exception != null)
                updateUserInformation.postValue(Resource.Error(exception))
                    .also { Log.d("test1", "up") }
            else
                user?.let {
                    onUpdateInformation(user).also { Log.d("test1", "down") }
                }
        }
    }

    private fun onUpdateInformation(user: User) {
        firebaseDatabase.updateUserInformation(user).addOnCompleteListener {
            if (it.isSuccessful)
                updateUserInformation.postValue(Resource.Success(user))
            else
                updateUserInformation.postValue(Resource.Error(it.exception.toString()))

        }
    }

    fun getUserOrders() {
        userOrders.postValue(Resource.Loading())
        firebaseDatabase.getUserOrders().addOnCompleteListener {
            if (it.isSuccessful)
                userOrders.postValue(Resource.Success(it.result?.toObjects(Order::class.java)))
            else
                userOrders.postValue(Resource.Error(it.exception.toString()))
        }
    }

    fun resetPassword(email: String) {
        passwordReset.postValue(Resource.Loading())
        firebaseDatabase.resetPassword(email).addOnCompleteListener {
            if (it.isSuccessful)
                passwordReset.postValue(Resource.Success(email))
            else
                passwordReset.postValue(Resource.Error(it.exception.toString()))
        }
    }

    fun getOrderAddressAndProducts(order: Order) {
        orderAddress.postValue(Resource.Loading())
        orderProducts.postValue(Resource.Loading())
        firebaseDatabase.getOrderAddressAndProducts(order, { address, aError ->
            if (aError != null)
                orderAddress.postValue(Resource.Error(aError))
            else
                orderAddress.postValue(Resource.Success(address))
        }, { products, pError ->

            if (pError != null)
                orderProducts.postValue(Resource.Error(pError))
            else
                orderProducts.postValue(Resource.Success(products))

        })
    }

    fun searchProducts(searchQuery: String) {
        search.postValue(Resource.Loading())
        firebaseDatabase.searchProducts(searchQuery).addOnCompleteListener {
            if (it.isSuccessful) {
                val productsList = it.result!!.toObjects(Product::class.java)
                search.postValue(Resource.Success(productsList))

            } else
                search.postValue(Resource.Error(it.exception.toString()))

        }
    }

    private var categoriesSafe: List<Category>? = null
    fun getCategories() {
        if(categoriesSafe != null){
            categories.postValue(Resource.Success(categoriesSafe))
            return
        }
        categories.postValue(Resource.Loading())
        firebaseDatabase.getCategories().addOnCompleteListener {
            if (it.isSuccessful) {
                val categoriesList = it.result!!.toObjects(Category::class.java)
                categoriesSafe = categoriesList
                categories.postValue(Resource.Success(categoriesList))
            } else
                categories.postValue(Resource.Error(it.exception.toString()))
        }


    }

}