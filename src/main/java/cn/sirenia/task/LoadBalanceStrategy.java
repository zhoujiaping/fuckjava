package cn.sirenia.task;

public enum LoadBalanceStrategy {
    /**随机*/
    Random,
    /**轮询*/
    RoundRobin,
    /**最少活跃数调用法*/
    LeastActive,
    /**一致性哈希*/
    ConsistentHash
}
