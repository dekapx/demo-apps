package net.dekapx.demoapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "trades")
public class Trade extends AbstractBaseEntity {
}
