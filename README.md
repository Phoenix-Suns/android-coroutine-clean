# android-mvi-coroutine-demo

![alt text](demo1.png "Title")

```mermaid

flowchart TD
 subgraph subGraph0["Khởi tạo Hilt trong App"]
        Z["MyApplication<br>@HiltAndroidApp"]
  end
 subgraph subGraph1["UI Layer (Compose)"]
        B["RepoListViewModel<br>@HiltViewModel"]
        A["Composable Screen<br>@Composable"]
  end
 subgraph subGraph2["ViewModel Layer"]
        C["GetRepoListUseCase<br>@Inject constructor"]
  end
 subgraph subGraph3["Domain Layer"]
        D["GithubUserRepository<br>(Interface)"]
  end
 subgraph subGraph4["Data Layer"]
        E["GithubUserRepositoryImpl<br>@Inject constructor"]
        F["...<br>Retrofit, Context, GithubWS, etc."]
  end
 subgraph subGraph5["Hilt Modules"]
        J["RepositoryModule<br>@Module, @Binds"]
  end
    A -- Yêu cầu ViewModel --> B
    B -- Gọi UseCase --> C
    C -- Phụ thuộc vào Interface --> D
    F -- Lấy phụ thuộc bằng @Inject --> E
    J -- Kết nối Interface với Implementation --> E
    D -- Kết nối Interface với Implementatio --> J

    style Z fill:#a2d5a2,stroke:#28a745,stroke-width:2px
    style B fill:#cde4ff
    style A fill:#cde4ff
    style C fill:#d4ffcd
    style D fill:#d4ffcd
    style E fill:#fff0cd
    style F fill:#fff0cd,stroke-dasharray: 5 5
    style J fill:#f9f,stroke-width:2px,color:#000
    linkStyle 0 stroke:#0077c2,stroke-width:2px,fill:none
    linkStyle 1 stroke:#28a745,stroke-width:2px,fill:none
    linkStyle 2 stroke:#fd7e14,stroke-width:2px,fill:none
    linkStyle 3 stroke:#6f42c1,stroke-width:2px,fill:none
    linkStyle 4 stroke-dasharray: 5 5,fill:none
    linkStyle 5 stroke:#dc3545,stroke-width:2px,fill:none

```