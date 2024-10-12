package self.tranluunghia.mvicoroutine.presentation.app

// ===== Tutorial =====
// https://nikolaymiroshnychenko.medium.com/the-ultimate-guide-to-dependency-injection-in-android-part-2-manual-di-c72a69239d7f
/*class AppContainer {

    private val db =
        Room.databaseBuilder(applicationContext, MyDatabase::class.java, "my-database-name").build()

    private val localDataSource = ShopLocalDataSource(db)
    private val remoteDataSource = ShopRemoteDataSource()

    private val shopRepository = ShopRepository(localDataSource, remoteDataSource)

    val shopViewModel = ShopViewModel(shopRepository)
}

class MyApplication: MultiDexApplication() {
    val appContainer = AppContainer()
}

// ===== Call ViewModel from Activity =====
shopViewModel = (applicationContext as MyApplication).appContainer.shopViewModelFactory.create()
*/


// ===== Dependencies với nhiều khởi tạo khác nhau =====
/*
 interface Factory<T> {
     fun create(): T
 }

class ShopViewModelFactory(private val shopRepository: ShopRepository) : Factory<ShopViewModel> {
    override fun create(): ShopViewModel {
        return ShopViewModel(shopRepository)
    }
}

class AppContainer {
    private val shopRepository = ShopRepository(localDataSource, remoteDataSource)
    val shopViewModelFactory = ShopViewModelFactory(shopRepository)
}*/
