package cc.star.datasource.loadBalance;

/**
 * 负载实体Bean
 */
public class LoadBlanceBean {

    /**
     * 数据源名称
     */
    private String dataSourceName;

    /**
     * 对应权重比
     */
    private Integer weight;


    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public LoadBlanceBean(String dataSourceName, Integer weight) {
        this.dataSourceName = dataSourceName;
        this.weight = weight;
    }
}
